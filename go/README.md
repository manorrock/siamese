# Manorrock Siamese CLI

Manorrock Siamese CLI delivers you with a command line binary that can be used to
perform executions, either local or remote.

## Using the Local executor

The Local executor allows you to perform an execution locally.

The command line below will execute 'echo 1234' locally and present you with the result.

```
  siamese local --arguments 'echo 1234'
```

## Using the Docker executor

The Docker executor allows you to perform an execution using a Docker container.

The command line below will execute 'echo 1234' on a docker container using the
manorrock/debian image and present you with the result.

```
  siamese docker --image manorrock/debian --arguments 'echo 1234'
```

## Using the SSH executor

The SHH executor allows you to perform an execution on a remote SSH host.

The command line below will execute 'echo 1234' on a remote SSH host and 
present you with the result.

```
  siamese ssh --username myusername --hostname myhostname --password mypassword
     --arguments 'echo 1234'
```

## Using the Kubernetes executor

The Kubernetes executor allows you to perform an execution on a Kubernetes cluster.

The command line below will execute 'echo 1234' on a Kubernetes cluster and 
present you with the result.

```
  siamese kubernetes --arguments 'echo 1234'
```
