package resourceServer;

import resources.TestResource;

public class ResourceServer implements ResourceServerInt {

    private TestResource testResource;

    @Override
    public int getAge() {
        return testResource.getAge();
    }

    @Override
    public String getName() {
        return testResource.getName();
    }

    @Override
    public void setAge(int age) {
        testResource.setAge(age);
    }

    @Override
    public void setName(String name) {
        testResource.setName(name);
    }

    @Override
    public void setTestResource(TestResource testResource) {
        this.testResource = testResource;
    }
}
