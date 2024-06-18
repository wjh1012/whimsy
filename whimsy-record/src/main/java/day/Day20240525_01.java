package day;

/**
 * @author jh.wang
 * @since 2024/5/25
 */

public class Day20240525_01 {
    public static void main(String[] args) throws CloneNotSupportedException {
        final Employee zhangsan = new Employee("zhangsan", 20);
        final Employee cloneZhangsan = (Employee) zhangsan.clone();
        System.out.println("clone == zhangsan = " + (cloneZhangsan == zhangsan));

        cloneZhangsan.setAge(21);
        System.out.println("cloneZhangsan = " + cloneZhangsan + " zhangsan = " + zhangsan);



        Employee lisi = zhangsan;
        System.out.println("lisi == zhangsan = " + (lisi == zhangsan));

        lisi.setAge(21);
        System.out.println("lisi = " + lisi + " zhangsan = " + zhangsan);

        /* result
        clone == zhangsan = false
        cloneZhangsan = Employee [name=zhangsan, age=21] zhangsan = Employee [name=zhangsan, age=20]
        lisi == zhangsan = true
        lisi = Employee [name=zhangsan, age=21] zhangsan = Employee [name=zhangsan, age=21]
         */
    }

    static class Employee implements Cloneable {
        String name;
        Integer age;

        public Employee(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Employee [name=" + name + ", age=" + age + "]";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}


