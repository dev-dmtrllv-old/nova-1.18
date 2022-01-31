GRADLE = ./gradlew

build: FORCE
	$(GRADLE) clean
	$(GRADLE) build
	rm -rf /home/dmtrllv/mc-server-test/mods/nova-1.0.jar
	rm -rf /home/dmtrllv/.minecraft/mods/nova-1.0.jar
	cp ./build/libs/nova-1.0.jar /home/dmtrllv/mc-server-test/mods/nova-1.0.jar
	cp ./build/libs/nova-1.0.jar /home/dmtrllv/.minecraft/mods/nova-1.0.jar

runServer: FORCE
	cd /home/dmtrllv/mc-server-test && ./run.sh

FORCE: ;
