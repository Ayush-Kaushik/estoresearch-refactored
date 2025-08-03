{ pkgs }: {
    deps = [
      pkgs.python39Packages.softlayer
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}