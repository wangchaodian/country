package com.qa.util;

import java.security.PrivateKey;

import basic.sunsharp.encrypt.AESUtils;
import basic.sunsharp.encrypt.RSAUtils;
import sun.misc.BASE64Decoder;

public class TJDecode {

     public static String decryptStr(String data) throws Exception {
      
    	 PrivateKey privateKey = RSAUtils.loadPrivateKey(TJDecode.class.getResourceAsStream("/tpri.key"));
//       data = "Sr5RmAygxf28ZiunL7psm+EXFdCsbOMJ0EkM0lt2hCfrbCxAx15Xin47Tuhu5qtpJuebRzflCx1nNaEQSu77rAq+X2gbQGQXNwyly/haWcj1cyAtSYmlSWWYkkxhH600wpXgJqzny1UwGxTMzc/rjWK5RfwYAkXS3gYbQaaATpqGrohZnzw6SUwrC23FbZBLKFrQ6q9mvr8w62dIpXAjPkzolZN/pON84K8FR35v8SpvkCs+EvfHk9X3JGw6fl4aLxbSpaB3vMb3yrC2HykCSrhYNbgJNm/xd/euv/i8e4KmFweDrEDnWOZLy4zrkftAbPfo5OkGos+gxD7eSS0TcQ==e/qtxJz3veAwU5qfRV1FoiuMHqwK5zsYRa4DBDMPRZCJLaKziv74TEFdQqOoTSsluzDij3gLD9NdQG4pkJdICdTIfsysmsxUnsTTJF45qBztmg78s7/+xu4l6gEaGPyaz5Xhhl56j6p/T4pQRWpqsYWWQdL23tv6XyfSE5OU0x1Eh2R2bPJs3TblRhKZX760VhY2eGmSu470b0vutVV5KDi1/5XmKIOzZPAYc8X/GkGj7KXisDsKhrFKAkgUozRxVhzbaO21tUDnjKvqsI+C3+YMLKiDC5basJTU/VwQlrE=";
        String aesKey = data.substring(0, 344);
        String body = data.substring(344, data.length());
        BASE64Decoder base64Decoder = new BASE64Decoder();

        byte[] aesKeyByte = base64Decoder.decodeBuffer(aesKey);
        byte[] aesKeyByte2 = RSAUtils.decryptData(aesKeyByte, privateKey);

        String aeskeys = new String(aesKeyByte2);
        String result = AESUtils.decryptStr(body, aeskeys);
        return result;
    }
   

}
