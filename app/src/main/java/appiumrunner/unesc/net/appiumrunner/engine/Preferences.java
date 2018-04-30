package appiumrunner.unesc.net.appiumrunner.engine;
import java.util.ArrayList;
import java.util.List;
public class Preferences {
    private boolean skip;
    private List<String> packages;
    public boolean isSkip() {
        return skip;
    }
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    public List<String> getPackages() {
        return packages;
    }
    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
    public void addImport(String packageName) {
        if (this.packages == null) {
            packages = new ArrayList<>();
        }
        this.packages.add(packageName);
    }
    public void addImports(List<String> packages) {
        if (this.packages == null) {
            packages = new ArrayList<>();
        }
        this.packages.addAll(packages);
    }
    public void skipMethodsDeclaration(boolean skip) {
        this.skip = skip;
    }
}
