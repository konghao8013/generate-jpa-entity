package entity;
import java.io.Serializable;
import javax.persistence.Entity; import javax.persistence.*;
@Entity
@Table(name = "tb_userinfo") 
public class Userinfo implements Serializable {
@Column(name = "name", nullable = false) 
private String name;
 public String getName() { return name; }
 public void setName(String name) { this.name = name; }
@Column(name = "age", nullable = false) 
private String age;
 public String getAge() { return age; }
 public void setAge(String age) { this.age = age; }
@Column(name = "address", nullable = false) 
private String address;
 public String getAddress() { return address; }
 public void setAddress(String address) { this.address = address; }
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false) 
private Integer id;
 public Integer getId() { return id; }
 public void setId(Integer id) { this.id = id; }
@Override
public String toString() {
return "Userinfo{"+"'name'='"+name+"'"+",'age'='"+age+"'"+",'address'='"+address+"'"+",'id'='"+id+"'"+'}';}
}
