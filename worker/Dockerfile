FROM debian:bookworm-slim

COPY /build/native/nativeCompile/worker /worker

EXPOSE 9999 

ENTRYPOINT ["/worker"]
