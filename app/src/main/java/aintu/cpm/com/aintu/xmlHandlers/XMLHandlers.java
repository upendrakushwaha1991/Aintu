package aintu.cpm.com.aintu.xmlHandlers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import aintu.cpm.com.aintu.xmlGetterSetter.BillingTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.BrandMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EntityTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EstProofMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.FailureGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.FolloowupReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.IdentificationTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.JourneyPlanGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.LeadClosureReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.LoginGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.RejectionReasonAintuGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.ReteilerReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StatusMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreCategoryGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreFormateMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitedWithMasterGetterSetter;


/**
 * Created by yadavendras on 21-12-2016.
 */

public class XMLHandlers {


    // FAILURE XML HANDLER
    public static FailureGetterSetter failureXMLHandler(XmlPullParser xpp, int eventType) {
        FailureGetterSetter failureGetterSetter = new FailureGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("STATUS")) {
                        failureGetterSetter.setStatus(xpp.nextText());
                    }
                    if (xpp.getName().equals("ERRORMSG")) {
                        failureGetterSetter.setErrorMsg(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return failureGetterSetter;
    }

    // LOGIN XML HANDLER
    public static LoginGetterSetter loginXMLHandler(XmlPullParser xpp, int eventType) {
        LoginGetterSetter lgs = new LoginGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("RIGHTNAME")) {
                        lgs.setRIGHTNAME(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_VERSION")) {
                        lgs.setAPP_VERSION(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_PATH")) {
                        lgs.setAPP_PATH(xpp.nextText());
                    }
                    if (xpp.getName().equals("CURRENTDATE")) {
                        lgs.setCURRENTDATE(xpp.nextText());
                    }
                    if (xpp.getName().equals("Success")) {
                        lgs.setSuccess(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return lgs;
    }

    // VISIT_TYPE_MASTER XML HANDLER
    public static VisitTypeMasterGetterSetter VISITTYPEMASTERXMLHandler(XmlPullParser xpp, int eventType) {
        VisitTypeMasterGetterSetter visitTypeMasterGetterSetter = new VisitTypeMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        visitTypeMasterGetterSetter.setTable_VISIT_TYPE_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("VISIT_TYPE_ID")) {
                        visitTypeMasterGetterSetter.setVISIT_TYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("VISIT_TYPE")) {
                        visitTypeMasterGetterSetter.setVISIT_TYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitTypeMasterGetterSetter;
    }

    // VISITED_WITH_MASTER XML HANDLER

    public static VisitedWithMasterGetterSetter VISITEDWITHMASTERXMLHandler(XmlPullParser xpp, int eventType) {
        VisitedWithMasterGetterSetter visitedWithMasterGetterSetter = new VisitedWithMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        visitedWithMasterGetterSetter.setTable_VISITED_WITH_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("WVISIT_ID")) {
                        visitedWithMasterGetterSetter.setWVISIT_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("VISITED_WITH")) {
                        visitedWithMasterGetterSetter.setVISITED_WITH(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return visitedWithMasterGetterSetter;
    }

    // STATUS_MASTER XML HANDLER

    public static StatusMasterGetterSetter STATUS_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        StatusMasterGetterSetter statusMasterGetterSetter = new StatusMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        statusMasterGetterSetter.setTable_STATUS_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("STATUS_ID")) {
                        statusMasterGetterSetter.setSTATUS_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STATUS")) {
                        statusMasterGetterSetter.setSTATUS(xpp.nextText());
                    }
                    if (xpp.getName().equals("ALLOW_REJECTION_AINTU")) {
                        statusMasterGetterSetter.setALLOW_REJECTION_AINTU(xpp.nextText());
                    }
                    if (xpp.getName().equals("ALLOW_KYC_PITCH_DATE")) {
                        statusMasterGetterSetter.setALLOW_KYC_PITCH_DATE(xpp.nextText());
                    }
                    if (xpp.getName().equals("ALLOW_REASON_REJECTION_RETAILER")) {
                        statusMasterGetterSetter.setALLOW_REASON_REJECTION_RETAILER(xpp.nextText());
                    }
                    if (xpp.getName().equals("ALLOW_REASON_FOLLOWUP")) {
                        statusMasterGetterSetter.setALLOW_REASON_FOLLOWUP(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return statusMasterGetterSetter;
    }

    // RETAILER_REASON_MASTER XML HANDLER

    public static ReteilerReasonMasterGetterSetter RETAILER_REASON_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        ReteilerReasonMasterGetterSetter reteilerReasonMasterGetterSetter = new ReteilerReasonMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        reteilerReasonMasterGetterSetter.setTable_RETAILER_REASON_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("RETAILER_REASON_ID")) {
                        reteilerReasonMasterGetterSetter.setRETAILER_REASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("RETAILER_REASON")) {
                        reteilerReasonMasterGetterSetter.setRETAILER_REASON(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reteilerReasonMasterGetterSetter;
    }

    // FOLLOWUP_REASON_MASTER XML HANDLER

    public static FolloowupReasonMasterGetterSetter FOLLOWUP_REASON_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        FolloowupReasonMasterGetterSetter folloowupReasonMasterGetterSetter = new FolloowupReasonMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        folloowupReasonMasterGetterSetter.setTable_FOLLOWUP_REASON_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("FREASON_ID")) {
                        folloowupReasonMasterGetterSetter.setFREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("FREASON")) {
                        folloowupReasonMasterGetterSetter.setFREASON(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return folloowupReasonMasterGetterSetter;
    }

    // STORE_FORMAT_MASTER XML HANDLER

    public static StoreFormateMasterGetterSetter STORE_FORMAT_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        StoreFormateMasterGetterSetter storeFormateMasterGetterSetter = new StoreFormateMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        storeFormateMasterGetterSetter.setTable_STORE_FORMAT_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("FORMAT_TYPE_ID")) {
                        storeFormateMasterGetterSetter.setFORMAT_TYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_FORMAT")) {
                        storeFormateMasterGetterSetter.setSTORE_FORMAT(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return storeFormateMasterGetterSetter;
    }

    // BRAND_MASTER XML HANDLER

    public static BrandMasterGetterSetter BRAND_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        BrandMasterGetterSetter brandMasterGetterSetter = new BrandMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        brandMasterGetterSetter.setTable_BRAND_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("BRAND_ID")) {
                        brandMasterGetterSetter.setBRAND_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND")) {
                        brandMasterGetterSetter.setBRAND(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return brandMasterGetterSetter;
    }


    // STORETYPE_MASTER XML HANDLER

    public static StoreTypeMasterGetterSetter STORETYPE_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        StoreTypeMasterGetterSetter storeTypeMasterGetterSetter = new StoreTypeMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        storeTypeMasterGetterSetter.setTable_STORETYPE_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORETYPE_ID")) {
                        storeTypeMasterGetterSetter.setSTORETYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_TYPE")) {
                        storeTypeMasterGetterSetter.setSTORE_TYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return storeTypeMasterGetterSetter;
    }

    // STORE SEARCH XML HANDLER
    public static StoreSearchGetterSetter STORESEARCHXMLHandler(XmlPullParser xpp, int eventType) {
        StoreSearchGetterSetter storeSearchGetterSetter = new StoreSearchGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        storeSearchGetterSetter.setTable_STORE_SEARCH(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORE_ID")) {
                        storeSearchGetterSetter.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_NAME")) {
                        storeSearchGetterSetter.setSTORE_NAME(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ADDRESS")) {
                        storeSearchGetterSetter.setSTORE_ADDRESS(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY_CD")) {
                        storeSearchGetterSetter.setCITY_CD(xpp.nextText());
                    }

                    if (xpp.getName().equals("CITY")) {
                        storeSearchGetterSetter.setCITY(xpp.nextText());
                    }

                    if (xpp.getName().equals("CONTACT_PERSON")) {
                        storeSearchGetterSetter.setCONTACT_PERSON(xpp.nextText());
                    }

                    if (xpp.getName().equals("OWNER_NAME")) {
                        storeSearchGetterSetter.setOWNER_NAME(xpp.nextText());
                    }
                    if (xpp.getName().equals("CONTACT_NUMBER")) {
                        storeSearchGetterSetter.setCONTACT_NUMBER(xpp.nextText());
                    }
                    if (xpp.getName().equals("GEOTAG")) {
                        storeSearchGetterSetter.setGEOTAG(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_PINCODE")) {
                        storeSearchGetterSetter.setSTORE_PINCODE(xpp.nextText());
                    }
                    if (xpp.getName().equals("MOBILE")) {
                        storeSearchGetterSetter.setMOBILE(xpp.nextText());
                    }
                    if (xpp.getName().equals("KYC")) {
                        storeSearchGetterSetter.setKYC(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return storeSearchGetterSetter;
    }

    // LEAD_CLOSURE_REASON_MASTER XML HANDLER

    public static LeadClosureReasonMasterGetterSetter LEAD_CLOSURE_REASON_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        LeadClosureReasonMasterGetterSetter leadClosureReasonMasterGetterSetter = new LeadClosureReasonMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        leadClosureReasonMasterGetterSetter.setTable_LEAD_CLOSURE_REASON_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("CREASON_ID")) {
                        leadClosureReasonMasterGetterSetter.setCREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CLOSURE_REASON")) {
                        leadClosureReasonMasterGetterSetter.setCLOSURE_REASON(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return leadClosureReasonMasterGetterSetter;
    }

    // ENTITY_TYPE_MASTER XML HANDLER

    public static EntityTypeMasterGetterSetter ENTITY_TYPE_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        EntityTypeMasterGetterSetter entityTypeMasterGetterSetter = new EntityTypeMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        entityTypeMasterGetterSetter.setTable_ENTITY_TYPE_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("ETYPE_ID")) {
                        entityTypeMasterGetterSetter.setETYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("ENTITY_TYPE")) {
                        entityTypeMasterGetterSetter.setENTITY_TYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entityTypeMasterGetterSetter;
    }

    // BILLING_TYPE_MASTER XML HANDLER

    public static BillingTypeMasterGetterSetter BILLING_TYPE_MASTERHandler(XmlPullParser xpp, int eventType) {
        BillingTypeMasterGetterSetter billingTypeMasterGetterSetter = new BillingTypeMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        billingTypeMasterGetterSetter.setTable_BILLING_TYPE_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("BTYPE_ID")) {
                        billingTypeMasterGetterSetter.setBTYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BILLING_TYPE")) {
                        billingTypeMasterGetterSetter.setBILLING_TYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billingTypeMasterGetterSetter;
    }

    // EST_PROOF_MASTER XML HANDLER

    public static EstProofMasterGetterSetter EST_PROOF_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        EstProofMasterGetterSetter estProofMasterGetterSetter = new EstProofMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        estProofMasterGetterSetter.setTable_EST_PROOF_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("EST_PROOF_ID")) {
                        estProofMasterGetterSetter.setEST_PROOF_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("EST_PROOF")) {
                        estProofMasterGetterSetter.setEST_PROOF(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return estProofMasterGetterSetter;
    }

    // IDENTIFICATION_TYPE_MASTER XML HANDLER

    public static IdentificationTypeMasterGetterSetter IDENTIFICATION_TYPE_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        IdentificationTypeMasterGetterSetter identificationTypeMasterGetterSetter = new IdentificationTypeMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        identificationTypeMasterGetterSetter.setTable_IDENTIFICATION_TYPE_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("PROOF_TYPE_ID")) {
                        identificationTypeMasterGetterSetter.setPROOF_TYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROOF_TYPE")) {
                        identificationTypeMasterGetterSetter.setPROOF_TYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return identificationTypeMasterGetterSetter;
    }


    // CITY_MASTER XML HANDLER

    public static CityMasterGetterSetter CITY_MASTERHandler(XmlPullParser xpp, int eventType) {
        CityMasterGetterSetter cityMasterGetterSetter = new CityMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        cityMasterGetterSetter.setTable_CITY_MASTER(xpp.nextText());
                    }

                    if (xpp.getName().equals("CITY_CD")) {
                        cityMasterGetterSetter.setCITY_CD(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY")) {
                        cityMasterGetterSetter.setCITY(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cityMasterGetterSetter;
    }


    // REJECTION_REASON_AINTU

    public static RejectionReasonAintuGetterSetter REJECTION_REASON_AINTUHandler(XmlPullParser xpp, int eventType) {
        RejectionReasonAintuGetterSetter rejectionReasonAintuGetterSetter = new RejectionReasonAintuGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        rejectionReasonAintuGetterSetter.setTable_REJECTION_REASON_AINTU(xpp.nextText());
                    }

                    if (xpp.getName().equals("AREASON_ID")) {
                        rejectionReasonAintuGetterSetter.setAREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("AREASON")) {
                        rejectionReasonAintuGetterSetter.setAREASON(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rejectionReasonAintuGetterSetter;
    }

    // STORE_CATEGORY

    public static StoreCategoryGetterSetter STORE_CATEGORYHandler(XmlPullParser xpp, int eventType) {
        StoreCategoryGetterSetter storeCategoryGetterSetter = new StoreCategoryGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        storeCategoryGetterSetter.setTable_STORE_CATEGORY(xpp.nextText());
                    }

                    if (xpp.getName().equals("CATEGORY_ID")) {
                        storeCategoryGetterSetter.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY")) {
                        storeCategoryGetterSetter.setCATEGORY(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return storeCategoryGetterSetter;
    }
/*
    public static JourneyPlanGetterSetter JCPXMLHandler(XmlPullParser xpp, int eventType) {
        JourneyPlanGetterSetter jcpGetterSetter = new JourneyPlanGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {


                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_journey_plan(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORE_ID")) {
                        jcpGetterSetter.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("EMP_ID")) {
                        jcpGetterSetter.setEMP_ID(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORE_NAME")) {
                        jcpGetterSetter.setSTORE_NAME(xpp.nextText());
                        //jcpGetterSetter.setSTORE_NAME("Dummy Store");
                    }
                    if (xpp.getName().equals("CITY")) {
                        jcpGetterSetter.setCITY(xpp.nextText());
                    }
                    if (xpp.getName().equals("VISIT_DATE")) {
                        jcpGetterSetter.setVISIT_DATE(xpp.nextText());
                    }
                    if (xpp.getName().equals("ADDRESS")) {
                        jcpGetterSetter.setADDRESS(xpp.nextText());
                        //jcpGetterSetter.setADDRESS("Dummy Address");
                    }
                    if (xpp.getName().equals("UPLOAD_STATUS")) {
                        jcpGetterSetter.setUPLOAD_STATUS(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORETYPE")) {
                        jcpGetterSetter.setSTORETYPE(xpp.nextText());
                    }

                    if (xpp.getName().equals("KEYACCOUNT_ID")) {
                        jcpGetterSetter.setKEYACCOUNT_ID(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORETYPE_ID")) {
                        jcpGetterSetter.setSTORETYPE_ID(xpp.nextText());
                    }

                    if (xpp.getName().equals("CHECKOUT_STATUS")) {
                        jcpGetterSetter.setCHECKOUT_STATUS(xpp.nextText());
//							jcpGetterSetter.setCHECKOUT_STATUS("N");
                    }

                    if (xpp.getName().equals("CLASSIFICATION")) {
                        jcpGetterSetter.setCLASSIFICATION(xpp.nextText());
                    }

                    if (xpp.getName().equals("KEYACCOUNT")) {
                        jcpGetterSetter.setKEYACCOUNT(xpp.nextText());
                    }

                    if (xpp.getName().equals("CLASS_ID")) {
                        jcpGetterSetter.setCLASS_ID(xpp.nextText());
                    }

                    if (xpp.getName().equals("CAMERA_ALLOW")) {
                        jcpGetterSetter.setCAMERA_ALLOW(xpp.nextText());
                    }

                    if (xpp.getName().equals("GEO_TAG")) {
                        jcpGetterSetter.setGEO_TAG(xpp.nextText());
                    }

                    if (xpp.getName().equals("CHANNEL_ID")) {
                        jcpGetterSetter.setCHANNEL_ID(xpp.nextText());
                    }

                    if (xpp.getName().equals("VISIT_ORDER")) {
                        jcpGetterSetter.setVISIT_ORDER(xpp.nextText());
                    }



                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }
*/

    // SKU_MASTER XML HANDLER
   /* public static SkuMasterGetterSetter skuMasterXMLHandler(XmlPullParser xpp,
                                                            int eventType) {
        SkuMasterGetterSetter sku = new SkuMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        sku.setTable_SKU_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_ID")) {
                        sku.setSKU_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU")) {
                        sku.setSKU(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_ID")) {
                        sku.setBRAND_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("MRP")) {
                        sku.setMRP(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_SEQUENCE")) {
                        sku.setSKU_SEQUENCE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sku;
    }

    // BRAND_MASTER XML HANDLER
    public static BrandMasterGetterSetter brandMasterXMLHandler(XmlPullParser xpp,
                                                                int eventType) {
        BrandMasterGetterSetter brand = new BrandMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        brand.setTable_BRAND_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_ID")) {
                        brand.setBRAND_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND")) {
                        brand.setBRAND(xpp.nextText());
                    }
                    if (xpp.getName().equals("SUB_CATEGORY_ID")) {
                        brand.setSUB_CATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("COMPANY_ID")) {
                        brand.setCOMPANY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_SEQUENCE")) {
                        brand.setBRAND_SEQUENCE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return brand;
    }

    // SUB_CATEGORY_MASTER XML HANDLER
    public static SubCategoryMasterGetterSetter subCategoryMasterXMLHandler(XmlPullParser xpp,
                                                                            int eventType) {
        SubCategoryMasterGetterSetter category = new SubCategoryMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        category.setTable_SUB_CATEGORY_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("SUB_CATEGORY_ID")) {
                        category.setSUB_CATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SUB_CATEGORY")) {
                        category.setSUB_CATEGORY(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_ID")) {
                        category.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SUB_CATEGORY_SEQUENCE")) {
                        category.setSUB_CATEGORY_SEQUENCE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return category;
    }

    // CATEGORY_MASTER XML HANDLER
    public static CategoryMasterGetterSetter categoryMasterXMLHandler(XmlPullParser xpp,
                                                                      int eventType) {
        CategoryMasterGetterSetter category = new CategoryMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        category.setTable_CATEGORY_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_ID")) {
                        category.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY")) {
                        category.setCATEGORY(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_SEQUENCE")) {
                        category.setCATEGORY_SEQUENCE(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return category;
    }

    // DISPLAY_MASTER XML HANDLER
    public static DisplayMasterGetterSetter displayMasterXMLHandler(XmlPullParser xpp,
                                                                    int eventType) {
        DisplayMasterGetterSetter display = new DisplayMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        display.setTable_DISPLAY_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY_ID")) {
                        display.setDISPLAY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY")) {
                        display.setDISPLAY(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_URL")) {
                        display.setIMAGE_URL(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_PATH")) {
                        display.setIMAGE_PATH(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return display;
    }

    // MAPPING_STOCK XML HANDLER
    public static MappingStockGetterSetter mappingStockXMLHandler(XmlPullParser xpp,
                                                                  int eventType) {
        MappingStockGetterSetter stock = new MappingStockGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        stock.setTable_MAPPING_STOCK(xpp.nextText());
                    }
                    if (xpp.getName().equals("KEYACCOUNT_ID")) {
                        stock.setKEYACCOUNT_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE_ID")) {
                        stock.setSTORETYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CLASS_ID")) {
                        stock.setCLASS_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_ID")) {
                        stock.setSKU_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("MUST_HAVE")) {
                        stock.setMUST_HAVE(xpp.nextText());
                    }
                    if (xpp.getName().equals("MBQ")) {
                        stock.setMBQ(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return stock;
    }

    // MAPPING_T2P XML HANDLER
    public static MAPPINGT2PGetterSetter mappingT2pXMLHandler(XmlPullParser xpp,
                                                              int eventType) {
        MAPPINGT2PGetterSetter t2p = new MAPPINGT2PGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        t2p.setTable_MAPPING_T2P(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ID")) {
                        t2p.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_ID")) {
                        t2p.setBRAND_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY_ID")) {
                        t2p.setDISPLAY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_FIXTURE")) {
                        t2p.setCATEGORY_FIXTURE(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return t2p;
    }

    // DISPLAY_CHECKLIST_MASTER XML HANDLER
    public static DisplayChecklistMasterGetterSetter mappingDisplayChecklistMasterXMLHandler(XmlPullParser xpp,
                                                                                             int eventType) {
        DisplayChecklistMasterGetterSetter checklist = new DisplayChecklistMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        checklist.setTable_DISPLAY_CHECKLIST_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_ID")) {
                        checklist.setCHECKLIST_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST")) {
                        checklist.setCHECKLIST(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return checklist;
    }

    // MAPPING_DISPLAY_CHECKLIST XML HANDLER
    public static MappingDisplayChecklistGetterSetter mappingMappingDisplayChecklistXMLHandler(XmlPullParser xpp,
                                                                                               int eventType) {
        MappingDisplayChecklistGetterSetter checklist = new MappingDisplayChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        checklist.setTable_MAPPING_DISPLAY_CHECKLIST(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_ID")) {
                        checklist.setCHECKLIST_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY_ID")) {
                        checklist.setDISPLAY_ID(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return checklist;
    }

    // NON_WORKING_REASON XML HANDLER
    public static NonWorkingReasonGetterSetter nonWorkingReasonXMLHandler(XmlPullParser xpp,
                                                                          int eventType) {
        NonWorkingReasonGetterSetter reason = new NonWorkingReasonGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        reason.setTable_NON_WORKING_REASON(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON_ID")) {
                        reason.setREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON")) {
                        reason.setREASON(xpp.nextText());
                    }
                    if (xpp.getName().equals("ENTRY_ALLOW")) {
                        reason.setENTRY_ALLOW(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_ALLOW")) {
                        reason.setIMAGE_ALLOW(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return reason;
    }

    // MAPPING_PROMOTION XML HANDLER
    public static MappingPromotionGetterSetter mappingPromotionXMLHandler(XmlPullParser xpp, int eventType) {
        MappingPromotionGetterSetter t2p = new MappingPromotionGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        t2p.setTable_MAPPING_PROMOTION(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ID")) {
                        t2p.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_ID")) {
                        t2p.setSKU_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU")) {
                        t2p.setSKU(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMO_ID")) {
                        t2p.setPROMO_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMO")) {
                        t2p.setPROMO(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t2p;
    }

    //Gagan Start Code

    //MAPPING_ADDITIONAL_PROMOTION
    public static MAPPING_ADDITIONAL_PROMOTION_MasterGetterSetter mappingAdditionalPromotionXMLHandler(XmlPullParser xpp, int eventType) {
        MAPPING_ADDITIONAL_PROMOTION_MasterGetterSetter map = new MAPPING_ADDITIONAL_PROMOTION_MasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        map.setTable_MAPPING_ADDITIONAL_PROMOTION(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ID")) {
                        map.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_ID")) {
                        map.setSKU_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU")) {
                        map.setSKU(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMO_ID")) {
                        map.setPROMO_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMO")) {
                        map.setPROMO(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static STORE_PERFORMANCE_MasterGetterSetter STORE_PERFORMANCEXMLHandler(XmlPullParser xpp, int eventType) {
        STORE_PERFORMANCE_MasterGetterSetter st = new STORE_PERFORMANCE_MasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_STORE_PERFORMANCE(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ID")) {
                        st.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_ID")) {
                        st.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PERIOD")) {
                        st.setPERIOD(xpp.nextText());
                    }
                    if (xpp.getName().equals("SOS")) {
                        st.setSOS(xpp.nextText());
                    }
                    if (xpp.getName().equals("T2P")) {
                        st.setT2P(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMO")) {
                        st.setPROMO(xpp.nextText());
                    }
                    if (xpp.getName().equals("MSL")) {
                        st.setMSL_AVAILABILITY(xpp.nextText());
                    }
                    if (xpp.getName().equals("OSS")) {
                        st.setOSS(xpp.nextText());
                    }
                    if (xpp.getName().equals("ORDERID")) {
                        st.setORDERID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PLANOGRAM")) {
                        st.setPLANOGRAM(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static MAPPING_PLANOGRAM_MasterGetterSetter MAPPING_PLANOGRAM_XMLHandler(XmlPullParser xpp, int eventType) {
        MAPPING_PLANOGRAM_MasterGetterSetter st = new MAPPING_PLANOGRAM_MasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_MAPPING_PLANOGRAM(xpp.nextText());
                    }
                    if (xpp.getName().equals("KEYACCOUNT_ID")) {
                        st.setKEYACCOUNT_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE_ID")) {
                        st.setSTORETYPE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CLASS_ID")) {
                        st.setCLASS_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_ID")) {
                        st.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("PLANOGRAM_IMAGE")) {
                        st.setPLANOGRAM_IMAGE(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_PATH")) {
                        st.setIMAGE_PATH(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
    //Gagan End Code


    public static ADDITIONAL_DISPLAY_MASTERGetterSetter ADDITIONAL_DISPLAY_MASTERXMLHandler(XmlPullParser xpp, int eventType) {
        ADDITIONAL_DISPLAY_MASTERGetterSetter st = new ADDITIONAL_DISPLAY_MASTERGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_STORE_ADDITIONAL_DISPLAY(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY_ID")) {
                        st.setDISPLAY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("DISPLAY")) {
                        st.setDISPLAY(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_URL")) {
                        st.setIMAGE_URL(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_PATH")) {
                        st.setIMAGE_PATH(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static MAPPING_SOS_TARGET_MasterGetterSetter MAPPING_SOS_TARGETXMLHandler(XmlPullParser xpp, int eventType) {
        MAPPING_SOS_TARGET_MasterGetterSetter st = new MAPPING_SOS_TARGET_MasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_MAPPING_SOS_TARGET(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_ID")) {
                        st.setSTORE_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_ID")) {
                        st.setBRAND_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SOS_TARGET")) {
                        st.setSOS_TARGET(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static ShelfMasterGetterSetter shelfMasterXMLHandler(XmlPullParser xpp, int eventType) {
        ShelfMasterGetterSetter st = new ShelfMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_SHELF_MASTER(xpp.nextText());
                    }
                    if (xpp.getName().equals("SHELF_ID")) {
                        st.setSHELF_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SHELF")) {
                        st.setSHELF(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }*/

/*
    public static MappingSubCategoryImageAllowGetterSetter mappingSubCategoryImageAllowXMLHandler(XmlPullParser xpp, int eventType) {
        MappingSubCategoryImageAllowGetterSetter st = new MappingSubCategoryImageAllowGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        st.setTable_MAPPING_SUB_CATEGORY_IMAGE_ALLOW(xpp.nextText());
                    }
                    if (xpp.getName().equals("COUNTRY_ID")) {
                        st.setCOUNTRY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("SUB_CATEGORY_ID")) {
                        st.setSUB_CATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_ALLOW")) {
                        st.setIMAGE_ALLOW(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
*/

}
