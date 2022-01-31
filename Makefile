GRADLE = ./gradlew

COPY_DIRS := ~/.minecraft/mods ~/mc-server-test/mods 
COPY_PATHS := $(patsubst %, %/nova.jar, $(COPY_DIRS))
MOD_JAR := ./build/libs/nova-0.1.0.jar

clean:
	rm -rf .gradle
	rm -rf .vscode
	rm -rf bin
	rm -rf build
	rm -rf run
	$(GRADLE) genVsCodeRuns

build: FORCE
	$(GRADLE) clean
	$(GRADLE) build
	rm -rf $(COPY_PATHS)
	tee $(COPY_PATHS) < $(MOD_JAR) >/dev/null

runServer: FORCE
	cd /home/dmtrllv/mc-server-test && ./run.sh

FORCE: ;
