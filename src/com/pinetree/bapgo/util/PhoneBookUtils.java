package com.pinetree.bapgo.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.gcm.GCMRegistrar;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.pinetree.bapgo.model.ModelMember;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneBookUtils {
	
	public static ArrayList<ModelMember> getPhoneBook(Context context){
		ArrayList<ModelMember> dataList = new ArrayList<ModelMember>();
		
		Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		String[] projectionContacts = new String[]{
				CommonDataKinds.Phone.CONTACT_ID,
				CommonDataKinds.Phone.HAS_PHONE_NUMBER,
				CommonDataKinds.Phone.DISPLAY_NAME,
				CommonDataKinds.Phone.NUMBER
		};
		
		Cursor contacts = context.getContentResolver().query(
				contactsUri,
				projectionContacts,
				CommonDataKinds.Phone.HAS_PHONE_NUMBER + "='1'",
				null,
				CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		
		
		ArrayList<Long> contactList = new ArrayList<Long>();
		
		while(contacts.moveToNext()){
			ModelMember itemMember;

			int columnContactId = contacts.getColumnIndex(CommonDataKinds.Phone.CONTACT_ID);
			int columnDisplayName = contacts.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME);
			
			long contactId = contacts.getLong(columnContactId);
			String displayName = contacts.getString(columnDisplayName);

			// raw_id 중복 확인
			if(contactList.contains(contactId)){
				int rawIdx = contactList.indexOf(contactId);
				itemMember = dataList.get(rawIdx);
			}else{
				itemMember = new ModelMember();
				
				dataList.add(itemMember);
				itemMember = dataList.get(dataList.size()-1); 
				itemMember.setContactId(contactId)
				.setName(displayName);
				
				contactList.add(contactId);
			}
			
			int columnPhoneNumber = contacts.getColumnIndex(CommonDataKinds.Phone.NUMBER);
			String phoneNumber = contacts.getString(columnPhoneNumber);
			
			String globalPhoneNumber = PhoneBookUtils.getGlobalPhoneNumber(phoneNumber);
			if(globalPhoneNumber!=null)
				itemMember.addPhoneNumber(globalPhoneNumber);
			else{
				contactList.remove(contactId);
				dataList.remove(itemMember);
			}
			/*
			if(!contactList.contains(contactId)){
				dataList.add(itemMember);
			}
			*/
		}
		contacts.close();
		
		return dataList;
	}
	
	public static String getGlobalPhoneNumber(String phoneNo){
		if("".equals(phoneNo) || phoneNo == null || phoneNo.length()<10)
			return null;

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		
		if(phoneNo.contains("-"))
			phoneNo = phoneNo.replaceAll("-", "");
		
		if(phoneNo.charAt(0)!='0' && phoneNo.charAt(0)!='+'){
			phoneNo = "+"+phoneNo;
			//Log.i("DebugPrint","add + :" + phoneNo);
		}
		
		/*
		 * todo usim 없을땐
		 */
		PhoneNumber thisPhoneNumber = getPhoneNumber(phoneNo);
		if(thisPhoneNumber!=null)
			return phoneUtil.format(getPhoneNumber(phoneNo), PhoneNumberFormat.E164);
		
		return phoneNo;
	}
	
	public static String getLocalPhoneNumber(String phoneNo){
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		return phoneUtil.formatOutOfCountryCallingNumber(getPhoneNumber(phoneNo), PhoneBookUtils.getSimCountryIso().toUpperCase());
	}
	
	protected static PhoneNumber getPhoneNumber(String phoneNo){
		if("".equals(phoneNo) || phoneNo == null)
			return null;
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumber = null;
		try {
			if(PhoneBookUtils.isUsimRight())
				phoneNumber = phoneUtil.parse(phoneNo, PhoneBookUtils.getSimCountryIso().toUpperCase());
			else
				phoneNumber = phoneUtil.parse(phoneNo, "KR");
				
		} catch (NumberParseException e) {
			// TODO Auto-generated catch block
			Log.i("DebugPrint", "NumberParseException:"+e.getMessage());
			e.printStackTrace();				
		}
		
		if(phoneUtil.isValidNumber(phoneNumber))
			return phoneNumber;
		else
			return null;
	}
	

	public static ArrayList<ModelMember> getSortRegisteredMemberList(ArrayList<ModelMember> itemList){
		ArrayList<ModelMember> registeredList = new ArrayList<ModelMember>();
		ArrayList<ModelMember> unregisteredList = new ArrayList<ModelMember>();
		ArrayList<ModelMember> sortedList = new ArrayList<ModelMember>();
		
		for(int i=0; i<itemList.size(); i++){
			if(itemList.get(i).getMemberSrl()>0){
				//Log.i("DebugPrint","regMember:"+itemList.get(i).getName());
				registeredList.add(itemList.get(i));
			}else{
				unregisteredList.add(itemList.get(i));
			}	
		}
		
		sortedList.addAll(registeredList);
		sortedList.addAll(unregisteredList);
		
		return sortedList;
		//return list;
	}
	

	public static boolean isUsimRight(){
		TelephonyManager mTelephonyMgr = (TelephonyManager) ActivityUtils.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
		
		if(mTelephonyMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT)
			return false;
		else
			return true;
	}
	
	public static String getMyPhoneNumber(){
		if(ActivityUtils.getInstance().getContext()==null || !PhoneBookUtils.isUsimRight())
			return null;
		
		TelephonyManager mTelephonyMgr = (TelephonyManager) ActivityUtils.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String myNumber = mTelephonyMgr.getLine1Number();
		myNumber = PhoneBookUtils.getGlobalPhoneNumber(myNumber);
		return myNumber;
	}
	public static String getSimCountryIso(){
		if(ActivityUtils.getInstance().getContext()==null || !PhoneBookUtils.isUsimRight())
			return null;
		TelephonyManager mTelephonyMgr = (TelephonyManager) ActivityUtils.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getSimCountryIso();
	}
	
	
	public static String getMyDeviceId(){
		if(ActivityUtils.getInstance().getContext()==null)
			return "";
		return GCMRegistrar.getRegistrationId(ActivityUtils.getInstance().getContext());
	}
	
}
