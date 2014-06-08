package com.baranski.balancewidget;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver 
{

	public static final String SMS_EXTRA_NAME = "pdus";
	public static final String SMS_URI = "content://sms";	
	public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";    
    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;    
    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;    
    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;
	

    public static final byte[] PASSWORD = new byte[]{ 0x20, 0x32, 0x34, 0x47, (byte) 0x84, 0x33, 0x58 };    
	public void onReceive( Context context, Intent intent ) 
	{
		
        Bundle extras = intent.getExtras();        
        String messages = "";        
        if ( extras != null )
        {
          
            Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );               
            ContentResolver contentResolver = context.getContentResolver(); 
            for ( int i = 0; i < smsExtra.length; ++i )
            {
            	SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
            	String body = sms.getMessageBody().toString();
            	String address = sms.getOriginatingAddress();
                messages += "SMS from " + address + " :\n";                    
                messages += body + "\n";               
                putSmsToDatabase( contentResolver, sms );
            }            
            Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
        }
 
	}
	
	private void putSmsToDatabase( ContentResolver contentResolver, SmsMessage sms )
	{
		
        ContentValues values = new ContentValues();
        values.put( ADDRESS, sms.getOriginatingAddress() );
        values.put( DATE, sms.getTimestampMillis() );
        values.put( READ, MESSAGE_IS_NOT_READ );
        values.put( STATUS, sms.getStatus() );
        values.put( TYPE, MESSAGE_TYPE_INBOX );
        values.put( SEEN, MESSAGE_IS_NOT_SEEN );
        try
        {
        	String encryptedPassword = StringCryptor.encrypt( new String(PASSWORD), sms.getMessageBody().toString() ); 
        	values.put( BODY, encryptedPassword );
        }
        catch ( Exception e ) 
        { 
        	e.printStackTrace(); 
    	} 
        contentResolver.insert( Uri.parse( SMS_URI ), values );
	}
}
