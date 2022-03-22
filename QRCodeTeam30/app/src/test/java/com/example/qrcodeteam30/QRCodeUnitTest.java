package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;
import com.example.qrcodeteam30.modelclass.QRCode;
import org.junit.Test;

public class QRCodeUnitTest {
    @Test
    public void getterSetterContentTest1() {
        QRCode code = new QRCode();
        code.setQrCodeContent("This is a content of QR Code");
        assertEquals("This is a content of QR Code", code.getQrCodeContent());
    }

    @Test
    public void getterSetterContentTest2() {
        QRCode code = new QRCode();
        code.setQrCodeContent("New QR Code Content");
        assertEquals("New QR Code Content", code.getQrCodeContent());
    }

    @Test
    public void getterSetterContentTest3() {
        QRCode code = new QRCode();
        code.setQrCodeContent("This QR Code does not have the content");
        assertEquals("This QR Code does not have the content", code.getQrCodeContent());
    }

    @Test
    public void getterSetterLatitudeTest1() {
        QRCode code = new QRCode();
        code.setLatitude(4.567212);
        assertEquals(4.567212, code.getLatitude(), 0.00001);
    }

    @Test
    public void getterSetterLatitudeTest2() {
        QRCode code = new QRCode();
        code.setLatitude(10.23467);
        assertEquals(10.23467, code.getLatitude(), 0.00001);
    }

    @Test
    public void getterSetterLatitudeTest3() {
        QRCode code = new QRCode();
        code.setLatitude(-5.22238);
        assertEquals(-5.22238, code.getLatitude(), 0.00001);
    }

    @Test
    public void getterSetterLongtitudeTest1() {
        QRCode code = new QRCode();
        code.setLongitude(41.567212);
        assertEquals(41.567212, code.getLongitude(), 0.00001);
    }

    @Test
    public void getterSetterLongtitudeTest2() {
        QRCode code = new QRCode();
        code.setLongitude(56.12345);
        assertEquals(56.12345, code.getLongitude(), 0.00001);
    }

    @Test
    public void getterSetterLongtitudeTest3() {
        QRCode code = new QRCode();
        code.setLongitude(11.1111);
        assertEquals(11.1111, code.getLongitude(), 0.00001);
    }

    @Test
    public void getterSetterDateTest1() {
        QRCode code = new QRCode();
        code.setDate("Tue Dec  12 01:03:00 PST 2019");
        assertEquals("Tue Dec  12 01:03:00 PST 2019", code.getDate());
    }

    @Test
    public void getterSetterDateTest2() {
        QRCode code = new QRCode();
        code.setDate("Sun Feb  22 10:01:00 MT 2020");
        assertEquals("Sun Feb  22 10:01:00 MT 2020", code.getDate());
    }

    @Test
    public void getterSetterDateTest3() {
        QRCode code = new QRCode();
        code.setDate("Sun Aug 05 09:05:00 PDT 2001");
        assertEquals("Sun Aug 05 09:05:00 PDT 2001", code.getDate());
    }

    @Test
    public void getterSetterScoreTest1() {
        QRCode code = new QRCode();
        code.setScore(20.156);
        assertEquals(20.156, code.getScore(), 0.00001);
    }

    @Test
    public void getterSetterScoreTest2() {
        QRCode code = new QRCode();
        code.setScore(1045.234);
        assertEquals(1045.234, code.getScore(), 0.00001);
    }

    @Test
    public void getterSetterScoreTest3() {
        QRCode code = new QRCode();
        code.setScore(1000.123456);
        assertEquals(1000.123456, code.getScore(), 0.00001);
    }

    @Test
    public void getterSetterUsernameTest1() {
        QRCode code = new QRCode();
        code.setUsername("Vancouver");
        assertEquals("Vancouver", code.getUsername());
    }

    @Test
    public void getterSetterUsernameTest2() {
        QRCode code = new QRCode();
        code.setUsername("Tokyo");
        assertEquals("Tokyo", code.getUsername());
    }

    @Test
    public void getterSetterUsernameTest3() {
        QRCode code = new QRCode();
        code.setUsername("Berlin");
        assertEquals("Berlin", code.getUsername());
    }

    @Test
    public void getterSetterFormatTest1() {
        QRCode code = new QRCode();
        code.setFormat("Text");
        assertEquals("Text", code.getFormat());
    }

    @Test
    public void getterSetterFormatTest2() {
        QRCode code = new QRCode();
        code.setFormat("Letter");
        assertEquals("Letter", code.getFormat());
    }

    @Test
    public void getterSetterFormatTest3() {
        QRCode code = new QRCode();
        code.setFormat("Code");
        assertEquals("Code", code.getFormat());
    }

    @Test
    public void getterSetterCommentListReferenceTest1() {
        QRCode code = new QRCode();
        code.setCommentListReference("abc123");
        assertEquals("abc123", code.getCommentListReference());
    }

    @Test
    public void getterSetterCommentListReferenceTest2() {
        QRCode code = new QRCode();
        code.setCommentListReference("qwer0987");
        assertEquals("qwer0987", code.getCommentListReference());
    }

    @Test
    public void getterSetterCommentListReferenceTest3() {
        QRCode code = new QRCode();
        code.setCommentListReference("c4v5b6n7");
        assertEquals("c4v5b6n7", code.getCommentListReference());
    }

    @Test
    public void getterSetterRecordLocationTest1() {
        QRCode code = new QRCode();
        code.setRecordLocation(true);
        assertEquals(true, code.isRecordLocation());
    }

    @Test
    public void getterSetterRecordLocationTest2() {
        QRCode code = new QRCode();
        code.setRecordLocation(false);
        assertEquals(false, code.isRecordLocation());
    }
}
