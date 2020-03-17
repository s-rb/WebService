package resourceServer;

public class ResourceServerController implements ResourceServerControllerMBean {

    private final ResourceServerInt resourceServer;

    public ResourceServerController(ResourceServerInt resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public int getAge() {
        return resourceServer.getAge();
    }

    @Override
    public String getName() {
        return resourceServer.getName();
    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public void setName(String name) {

    }
}
