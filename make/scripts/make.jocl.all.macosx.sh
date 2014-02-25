#! /bin/sh

SDIR=`dirname $0` 

if [ -e /opt-share/etc/profile.ant ] ; then
    . /opt-share/etc/profile.ant
fi

JAVA_HOME=`/usr/libexec/java_home -version 1.7`
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME PATH

#    -Dc.compiler.debug=true 

export SOURCE_LEVEL=1.6
export TARGET_LEVEL=1.6
export TARGET_RT_JAR=/opt-share/jre1.6.0_30/lib/rt.jar

export GLUEGEN_PROPERTIES_FILE="../../gluegen/make/lib/gluegen-clang.properties"
# or -Dgcc.compat.compiler=clang

#export JOGAMP_JAR_CODEBASE="Codebase: *.jogamp.org"
export JOGAMP_JAR_CODEBASE="Codebase: *.goethel.localnet"

ant  \
    -Drootrel.build=build-macosx \
    $* 2>&1 | tee make.jocl.all.macosx.log