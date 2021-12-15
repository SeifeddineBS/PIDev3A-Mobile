/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

/**
 *
 * @author Chirine
 */
public interface LocalNotificationCallback {
    
    /**
     * <p>Callback method that is called when a local notification is received AND
     * the application is active. This won't necessarily be called when the 
     * notification is received.  If the app is in the background, for example, 
     * the notification will manifest itself as a message to the user's task bar
     * (or equivalent).  If the user then clicks on the notification message, the
     * app will be activated, and this callback method will be called.</p>
     * 
     * <p><em>IMPORTANT:  THIS CALLBACK IS CALLED OFF THE EDT.  ANY UPDATES TO THE UI
     * WILL NEED TO OCCUR INSIDE A <code>callSerially()</code> block.</em></p>
     * 
     * @param notificationId The notification ID of the notification that was received.
     * @see LocalNotification
     */
    public void localNotificationReceived(String notificationId);
}
