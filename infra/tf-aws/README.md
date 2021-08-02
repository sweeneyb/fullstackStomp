## Running this app on a new EC2 instance ##

### Assumptions ###
* You have terraform installed.  I'm using 1.0.3
* You have aws credentials

### Commands ### 
With ```AWS_ACCESS_KEY_ID``` and ```AWS_SECRET_ACCESS_KEY``` set per https://registry.terraform.io/providers/hashicorp/aws/latest/docs#environment-variables,

The single command to spin up the application is:
```terraform apply -auto-approve```

However, it is more customary to run a plan, review it, then apply that plan:
```
terraform plan -out tfplan
## <review plan>
terraform apply tfplan
```

## About ##
Being new to AWS, I made a few choices to make my life easier.  Running on Ubuntu 20.04 LTS eliminated a lot of unknowns about AMIs.  I know ubuntu machines support cloud-init, which makes provisioning without every logging onto the box possible.  Knowing that terraform and cloud-init work well together, I searched for an example of geting an EC2 instance up with a cloud-init script, and found https://learn.hashicorp.com/tutorials/terraform/cloud-init.  After working through a few small issues (I did provision some keys and log onto some instances to see how to make things work), the VM stands up, accepts traffic, and runs the application.

### Why systemd and not docker run --restart=always ###
There are 2 good options to launch a container if the system reboots.  Docker has a restart option, but I think system administrators would more typically look at systemd to see what's running on a machine.  Logs are accessible via journalctl like other services.  The tools are familiar to somebody that would be managing the box.

That said, this VM is intended to be ephemeral. If it has a problem, terminate the instance, and start another.  That would be my design for getting security updates, too.

## Next steps
The app running over bare HTTP isn't ideal.  AWS is interesting that it automatically provisionis a DNS name for the instance.  This could be used with certbot to automatically grab a TLS certificate from Let's Encrypt and placed into an nginx reverse proxy.  At that point, we can stop exposing the HTTP port outside of the VM and rework the firewall rules to only allow HTTPS traffic.