docker_build('sunwoo3856/authentication', '.')

k8s_yaml(kustomize('k8s/overlays/development'))

k8s_resource('auth-service', port_forwards=['9001'])