# VPNBypass

![Banner](https://cdn.stuf.io/8484tTW0tO9u1M.png)

This is a Java Application that has been made to create networking rules that allow users to access Websites without going through their VPN. This was done as I was finding that more and more websites and services were restricting access to users who were connecting through a VPN.

### Requirements
 - Java 1.8_U40 *(or higher*) - [Download](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
  - Windows XP *(or higher)*

### Download
You can download the compiled and wrapped executable [here](https://cdn.stuf.io/8585TWodb1w61N.exe), or you can clone the repository yourself and build it using maven.
However, to use the application you will need to wrap the compiled Jar in a Windows executable. This is further explained in the [Wrapping/Security](#wrapping-&-security) section

### Usage
Simply download or compile and wrap the application yourself and run it. You will be presented with a simple GUI, where you input the domain which you want to add a rule for. Click on 'Whitelist' and the application will resolve the associated addresses and create the appropriate network rules.

### Wrapping & Security
Due to the default permission restrictions that are set within Windows, the application needs to be run as Administrator. The easiest workaround I found was to wrap the Jar file in an executable and define the execution level in a manifest file.

I used Launch4J to wrap the Jar and add the manifest. You can get it here, [Launch4J Website](http://launch4j.sourceforge.net/).

The manifest file is included in the repository files, named [VPNBypass.manifest](../src/master/VPNBypass.manifest). It's a small file, which defines the requested execution level that's required. Thanks to [Ashium](https://stackoverflow.com/questions/19037339/run-java-file-as-administrator-with-full-privileges/22110928#22110928) @ Stackoverflow for this fix. I've included below the contents of the manifest file.
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<assembly xmlns="urn:schemas-microsoft-com:asm.v1" manifestVersion="1.0">
    <trustInfo xmlns="urn:schemas-microsoft-com:asm.v3">
        <security>
            <requestedPrivileges>
                <requestedExecutionLevel level="highestAvailable" uiAccess="False"/>
            </requestedPrivileges>
        </security>
    </trustInfo>
</assembly>
```
