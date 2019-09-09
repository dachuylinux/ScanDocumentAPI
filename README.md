# ScanDocumentAPI
### đối với máy có cài các driver máy in 32-bit:
B1. Thực hiện cài đặt java-8 32-bit.<br/>
B2. Copy file ./files/32bit/TWAINDSM.dll đưa vào thư mục C:\Windows\SysWOW64<br/>
B3. Mở file ./files/run_program.batch và chỉnh sửa giá trị java_path thành đường dẫn thư mục cài đặt java-8 32-bit<br/>
B4. Run file ./files/run_program.batch <br/>
### đối với máy có cài các driver máy in 64-bit:<br/>
B1. Thực hiện cài đặt java-8 64-bit.<br/>
B2. Copy file ./files/64bit/TWAINDSM.dll đưa vào thư mục C:\Windows\System32<br/>
B3. Mở file ./files/run_program.batch và chỉnh sửa giá trị java_path thành đường dẫn thư mục cài đặt java-8 32-bit<br/>
B4. Run file ./files/run_program.batch <br/>
### >>>mở file example.html lên và sử dụng
### Thông tin về API xem các hàm javascript được viết tương ứng với các action
200 >> file sẽ được lưu ở thư mục C:\Windows\Temp\ScanAPI\ <br/>
503 >> lỗi. <br/>
### chú ý, chương trình sẽ thực thi run embedded tomcat server với port 8080, để thay đổi port này, mở file ./files/run_program.batch và sửa giá trị -Dserver.port=<port_mong_muốn> , nhớ chỉnh sửa lại giá trị PORT trong file example.html
