This is the static code generation variant of the Node examples. 
Code in these examples is pre-generated using protoc and the Node gRPC protoc plugin, 
and the generated code can be found in various `*_pb.js` files. 

The command line sequence for generating those files is as follows 
(assuming that `protoc` and `grpc_node_plugin` are present, 
and starting in the directory which contains this README.md file):

```sh
cd ../protos

npm install -g grpc-tools

grpc_tools_node_protoc \n
    --js_out=import_style=commonjs,binary:../node/static_codegen/ \n
    --grpc_out=../node/static_codegen \n
    --plugin=protoc-gen-grpc=`which grpc_tools_node_protoc_plugin` \n
    helloworld.proto
    
grpc_tools_node_protoc \n
    --js_out=import_style=commonjs,binary:../node/static_codegen/route_guide/ \n
    --grpc_out=../node/static_codegen/route_guide/ \n
    --plugin=protoc-gen-grpc=`which grpc_tools_node_protoc_plugin` \n
    route_guide.proto
```
