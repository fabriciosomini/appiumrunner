package appiumrunner.unesc.net.appiumrunner.engine;

import java.util.ArrayList;
import java.util.List;

public class Preferences {
    private boolean skipMethodsDeclaration;
    private boolean skipTearDownDeclaration;
    private List<String> packages;
    private String testPackageName;
    private String extendedClass;

    public boolean isSkipTearDownDeclaration() {
        return skipTearDownDeclaration;
    }

    public void skipTearDownDeclaration(boolean skipTearDownDeclaration) {
        this.skipTearDownDeclaration = skipTearDownDeclaration;
    }

    public boolean isSkipMethodsDeclaration() {
        return skipMethodsDeclaration;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public void addImport(String packageName) {
        if (this.packages == null) {
            this.packages = new ArrayList<>();
        }
        this.packages.add(packageName);
    }

    public void addImports(List<String> packages) {
        if (this.packages == null) {
            this.packages = new ArrayList<>();
        }
        this.packages.addAll(packages);
    }

    public void skipMethodsDeclaration(boolean skip) {
        this.skipMethodsDeclaration = skip;
    }

    public String getTestPackageName() {
        return testPackageName;
    }

    public void setTestPackageName(String testPackageName) {
        this.testPackageName = testPackageName;
    }

    public String getExtendedClass() {
        return extendedClass;
    }

    public void setExtendedClass(String extendedClass) {
        this.extendedClass = extendedClass;
    }
}
