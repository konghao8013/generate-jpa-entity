package entity;
import java.io.Serializable;
import javax.persistence.Entity; import javax.persistence.*;
@Entity
@Table(name = "tb_userinfo") 
public class Userinfo implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false) 
private Integer id;
@Column(name = "name", nullable = false) 
private String name;
@Column(name = "age", nullable = false) 
private String age;
@Column(name = "address", nullable = false) 
private String address;
 public Integer getId() { return id; }
 public void setId(Integer id) { this.id = id; }
 public String getName() { return name; }
 public void setName(String name) { this.name = name; }
 public String getAge() { return age; }
 public void setAge(String age) { this.age = age; }
 public String getAddress() { return address; }
 public void setAddress(String address) { this.address = address; }
@Override
public String toString() {
return "Userinfo{"+"'id'='"+id+"'"+",'name'='"+name+"'"+",'age'='"+age+"'"+",'address'='"+address+"'"+'}';}
}
