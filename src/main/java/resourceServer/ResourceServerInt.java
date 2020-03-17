package resourceServer;

import resources.TestResource;

public interface ResourceServerInt {

    int getAge();
    String getName();
    void setAge(int age);
    void setName(String name);
    void setTestResource(TestResource testResource);
}
