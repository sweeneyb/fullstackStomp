@echo on

cd frontend
call npm run build
echo Exit Code is %errorlevel%
cp -r dist\ ..\fullstackGame\src\main\resources\static\
cd ..\fullstackGame
call gradlew build
cd ..
@rem cp fullstackGame/build/libs/*.jar infra\docker\app.jar
@rem cd infra\docker\
@rem call docker build . -t foo