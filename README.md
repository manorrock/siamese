# Manorrock Siamese

Manorrock Siamese is a continuous execution platform that is capable of
scaling effortlessly. Give it a try!

## Using the Docker image to deploy Manorrock Siamese.

```shell
  docker run --rm -d -p 8080:8080 manorrock/siamese:VERSION
```

Replace VERSION with the version of Manorrock Siamese you want to run.

## Using the Local executor

The Local executor allows you to perform an execution locally.

The command line below will execute 'echo 1234' locally and present you with the result.

```
  siamese local --arguments 'echo 1234'
```

## Using the Docker executor

The Docker executor allows you to perform an execution using a Docker container.

The command line below will execute 'echo 1234' on a Docker container using the
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

## How do I contribute

See [Contributing](CONTRIBUTING.md)

## Our code of Conduct

See [Code of Conduct](CODE_OF_CONDUCT.md)

## Important notice

Note if you file issues or answer questions on the issue tracker and/or issue
pull requests you agree that those contributions will be owned by Manorrock.com
and that Manorrock.com can use those contributions in any manner Manorrock.com
so desires.
