# ScanDocumentAPI
### đối với máy có cài các driver máy in 32-bit:
B1. Thực hiện cài đặt java-8 32-bit.<br/>
B2. Copy file ./files/32bit/TWAINDSM.dll đưa vào thư mục C:\Windows\SysWOW64<br/>
B3. Tìm đến thư mục cài đặt java-8 32-bit và run: java -d32 -jar <đường dẫn file>/scan-document-api-0.0.1-SNAPSHOT.jar<br/>
### đối với máy có cài các driver máy in 64-bit:<br/>
B1. Thực hiện cài đặt java-8 64-bit.<br/>
B2. Copy file ./files/64bit/TWAINDSM.dll đưa vào thư mục C:\Windows\System32<br/>
B3. Tìm đến thư mục cài đặt java-8 64-bit và run: java -jar <đường dẫn file>/scan-document-api-0.0.1-SNAPSHOT.jar<br/>
### call API
http://localhost:8080/scanPDF/multiplePages?colorMode=RBG&duplex=1&fileName=testx1<br/>
note tham số:<br/>
colorMode: chế độ in RBG: màu, BLW: đen trắng, GRAY: gray<br/>
duplex: 1. in cả 2 mặt, 0. in 1 mặt<br/>
fileName: tên file để push lên server.<br/>
### Sau khi call API response trả về: <br/>
200 >> file sẽ được lưu ở thư mục C:\Windows\Temp\ScanAPI\<fileName>.pdf.<br/>
503 >> lỗi.

