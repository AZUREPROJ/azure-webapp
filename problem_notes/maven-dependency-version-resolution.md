
# 📝 Note: Nguyên lý Maven Dependency & Version Resolution

## 1️⃣ Cơ bản về Dependency trong Maven

* Khi bạn khai báo một dependency trong `pom.xml`, Maven sẽ tải **POM của dependency đó** để:

    * Biết nó cần những dependency khác (gọi là *transitive dependencies*).
    * Biết phiên bản mặc định của các dependency con này.

➡️ Vì vậy, chỉ cần bạn khai báo 1 dependency, Maven có thể kéo theo hàng chục dependency khác mà bạn không hề viết trực tiếp trong `pom.xml`.

---

## 2️⃣ Xung đột version (Version Conflict)

* Giả sử:

    * `A` phụ thuộc `B:1.0`
    * `A` cũng phụ thuộc `D:1.0`, và `D` phụ thuộc `C:2.0`
    * Nhưng `B` lại phụ thuộc `C:1.0`

👉 Bạn có 2 đường dẫn khác nhau tới cùng một artifact `C` nhưng với **2 phiên bản khác nhau**:

* `A → B → C:1.0`
* `A → D → C:2.0`

---

## 3️⃣ Maven chọn phiên bản nào?

* **Quy tắc mặc định của Maven**: **Nearest-wins** (phiên bản từ dependency có khoảng cách gần hơn với root POM sẽ thắng).
* Nếu khoảng cách bằng nhau, Maven sẽ chọn **phiên bản được khai báo trước tiên** trong POM.

⚠️ Điều này có thể dẫn tới:

* Một thư viện bị load phiên bản quá cũ (thiếu API).
* Một thư viện bị load phiên bản quá mới (thay đổi API).
* Runtime lỗi như:

    * `NoSuchMethodError`
    * `ClassNotFoundException`
    * `UnsatisfiedDependencyException` (bean Spring không tạo được vì API mismatch).

---

## 4️⃣ Tác hại trong thực tế

* Bạn build thành công nhưng runtime fail, vì compile-time lấy đúng version, runtime lại lấy version khác.
* Ví dụ lỗi bạn gặp:

  ```
  TaskExecutorBuilder not found
  ```

  Do version của `spring-boot` và `spring-cloud-azure` kéo về 2 version khác nhau của thư viện core.

---

## 5️⃣ Best Practices để tránh conflict

✔️ **Dùng BOM (Bill of Materials)**

* Maven cho phép import một `dependencyManagement` BOM (ví dụ: `spring-cloud-azure-dependencies`).
* BOM định nghĩa **một bộ version đồng bộ** cho toàn bộ ecosystem → tránh xung đột.

✔️ **Sử dụng plugin maven-dependency-plugin**

* Chạy lệnh:

  ```bash
  mvn dependency:tree
  ```

  để kiểm tra xem artifact nào đang kéo về version nào.

✔️ **Khai báo dependencyManagement trong project**

* Nếu cần ép version cho toàn bộ dự án, khai báo trong `<dependencyManagement>` thay vì rải rác nhiều nơi.

---

## 6️⃣ Kết luận

* Maven **luôn chọn 1 version duy nhất** của mỗi artifact để bỏ vào classpath.
* Quy tắc "nearest-wins" có thể gây ra version không mong muốn.
* **Giải pháp an toàn**: luôn dùng BOM hoặc explicit `dependencyManagement` để kiểm soát version.

---
