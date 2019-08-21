# ScanDocumentAPI
### đối với máy có cài các driver máy in 32-bit:
B1. Thực hiện cài đặt java-8 32-bit.<br/>
B2. Copy file ./files/32bit/TWAINDSM.dll đưa vào thư mục C:\Windows\SysWOW64<br/>
B3. Tìm đến thư mục cài đặt java-8 32-bit và run: java -d32 -jar <đường dẫn file>/scan-document-api-0.0.1-SNAPSHOT.jar<br/>
### đối với máy có cài các driver máy in 64-bit:<br/>
copy file ./files/64bit/TWAINDSM.dll đưa vào thư mục C:\Windows\System32<br/>
B1. Thực hiện cài đặt java-8 64-bit.<br/>
B2. Copy file ./files/64bit/TWAINDSM.dll đưa vào thư mục C:\Windows\System32<br/>
B3. Tìm đến thư mục cài đặt java-8 64-bit và run: java -jar <đường dẫn file>/scan-document-api-0.0.1-SNAPSHOT.jar<br/>
