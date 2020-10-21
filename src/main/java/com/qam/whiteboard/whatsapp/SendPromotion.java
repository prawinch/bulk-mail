package com.qam.whiteboard.whatsapp;

public class SendPromotion {
    public static void main(String[] args) {
        WhatsappHelper whatsappHelper = new WhatsappHelper( "farm2Home", "f2h.xls");
      //  whatsappHelper.launchWhatsApp();
        whatsappHelper.findContactAndOpen();
    }
}
