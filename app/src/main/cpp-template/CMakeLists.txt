cmake_minimum_required(VERSION 3.22.1)
project(NativeApp LANGUAGES CXX)

add_library(native-lib SHARED main.cpp)
target_compile_features(native-lib PRIVATE cxx_std_17)
find_library(android-log log)
target_link_libraries(native-lib PRIVATE ${android-log})
