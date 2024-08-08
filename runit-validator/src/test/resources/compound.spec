Name:           compound
Version:        42
Release:        1
Summary:        A package
License:        Apache-2.0
URL:            https://kojan.io/
BuildArch:      noarch

%description
Some package.

%package sub
Summary: A subpackage

%description sub
This is a subpackage.

%package -n other
Summary: A subpackage

%description -n other
This is a subpackage.

%install
cd %{buildroot}
echo 1 >foo
mkdir -p opt/my.jar
echo 2 >opt/my.jar/bar
mkdir -p usr/share/java
echo 3 >usr/share/java/my.jar
mkdir -p usr/share/javadoc
echo 4 >usr/share/javadoc/index.html
echo 5 >otherfile

%files
/foo
/opt/my.jar/bar
%files sub
/usr/share/java/my.jar
%dir /usr/share/javadoc
/usr/share/javadoc/index.html
%files -n other
/otherfile

%changelog
