CHANNELS=(GFAN GOAPK)
android update project -p /home/melody/workspace/OriginReader -t 4 -n dqdg
cp AndroidManifest.xml AndroidManifest_bak.xml
cp build.xml build_bak.xml
for i in ${CHANNELS[@]}
do
	CHANNEL="$i"
	echo ${CHANNEL}
	sed "s/Development/$CHANNEL/g" AndroidManifest_bak.xml > AndroidManifest.xml
	sed "s/dqdg/dqdg_$CHANNEL/g" build_bak.xml > build.xml
	ant release
done
cp AndroidManifest_bak.xml AndroidManifest.xml
cp build_bak.xml build.xml
