package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;
import com.example.qrcodeteam30.modelclass.QRCode;
import org.junit.Test;

public class QRCodeUnitTest {
    @Test
    public void getterSetterUsernameTest1() {
        QRCode code = new QRCode();
        code.setUsername("user1");
        assertEquals("user1", code.getUsername());
    }
}
