#! /bin/sh

SDIR=`dirname $0` 

if [ -e $SDIR/../../gluegen/make/scripts/setenv-build-jogl-x86.sh ] ; then
    . $SDIR/../../gluegen/make/scripts/setenv-build-jogl-x86.sh
fi

ant  \
    -Drootrel.build=build-x86 \
    $* 2>&1 | tee make.jocl.all.linux-x86.log
