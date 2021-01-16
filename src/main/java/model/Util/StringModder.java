package model.Util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringModder {

    public static void main(String[] args) {
        String s[] = "'AF','AL','DZ','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BS','BH','BD','BB','BE','BZ','BJ','BM','BY','BT','BO','BA','BW','BR','BN','BG','BF','BI','KH','CM','CA','CV','TD','CL','CN','CY','VA','CO','KM','KP','KR','CR','CI','HR','CU','DK','DM','EC','EG','IE','SV','AE','ER','EE','ET','RU','FJ','PH','FI','FR','GA','GM','GE','DE','GH','JM','JP','GI','DJ','JO','GR','GD','GL','GP','GU','GT','GN','GW','GQ','GY','GF','HT','HN','HK','IN','ID','IR','IQ','BV','CX','HM','KY','CC','CK','FK','FO','MH','MP','UM','NF','SB','TC','VI','VG','IL','IS','IT','KZ','KE','KG','KI','KW','LA','LV','LS','LB','LR','LY','LI','LT','LU','MO','MK','MG','MW','MV','MY','ML','MT','MA','MQ','MR','MU','YT','MX','MD','MC','MN','MS','MZ','MM','NA','NR','NP','NI','NE','NG','NU','NO','NC','NZ','OM','NL','PK','PW','PA','PG','PY','PE','PN','PF','PL','PT','PR','QA','GB','CZ','CF','CG','CD','DO','RE','RO','RW','EH','KN','PM','VC','WS','AS','SM','SH','LC','ST','SN','XK','SC','SL','SG','SY','SK','SI','SO','ES','LK','FM','US','ZA','GS','SD','SR','SJ','SE','CH','SZ','TJ','TH','TW','TZ','IO','TF','PS','TL','TG','TK','TO','TT','TN','TR','TM','TV','UA','UG','HU','UY','UZ','VU','VE','VN','WF','YE','ZM','ZW','RS','ME','TP','GG'".split(",");
        for(String t : s){
            System.out.print(t.substring(1,3) + ",");
        }
//        for (int i = 0 ; i<10; i++) {
//            System.out.println("\"" + RandomStringUtils.random(6, true, true) + "\",");
//        }
    }

}
