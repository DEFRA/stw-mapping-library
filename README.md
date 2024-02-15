# Mapping Library

This repository allows for a mapping to be created from the
IPAFFS Notification JSON structure to the Microsoft Dynamics JSON structure.

## Git hook setup

1. Run `mvn install` to configure hooks

## IPAFFS -> Trade

### How to Peek at the Trade JSON locally

In order to successfully see changes made to the trade JSON mapping, you need
the [Microsoft Azure Service Bus Explorer Application](https://github.com/paolosalvatori/ServiceBusExplorer).
However, this is currently only available in Windows.

You can either use a Windows 10 Machine for these steps, or you can use a Windows 10 Virtual
Machine (VM).
For a list of comprehensive instructions on how to install a Windows 10 VM, check
out [this article](https://www.howtogeek.com/657464/how-to-install-a-windows-10-virtualbox-vm-on-macos/).

___

#### To Install and Open the Service Bus Explorer Application, follow these steps from within the VM:

1. From the Start Menu, type "Windows PowerShell". Right click the application and choose "Run as
   Administrator"
2. Paste these following commands
    ```powershell
    Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1')) 
    
    choco install ServiceBusExplorer
    
    C:\ProgramData\chocolatey\lib\ServiceBusExplorer\tools\ServiceBusExplorer.exe
    ```
3. The Application's GUI should open. You can create a shortcut to that .exe on your desktop for
   convenience

___

#### In order to easily see a message on the Queue, you can patch an existing notification by changing its Status in Postman

> Before these steps, ensure you have both `TRACES_NT_INTEGRATION` and `TRADE_PLATFORM_INTEGRATION`
> set to `true` in your Notification-MS `.env` file

1. Obtain the `etag` property from the notification JSON
2. Send a PATCH request to the URL `http://localhost:4560/notifications/<ref number>` with
   an `If-Match` header with the value of the `etag` obtained in Step 1.
   Set the Body to:
    ```json
    [
      {
        "op": "replace",
        "path": "/status",
        "value": "AMEND"
      }
    ]
    ```
3. You should see the following success message in the console output of the Notification-MS. If so,
   proceed to the next set of instructions
    ```
     Message successfully sent to topic: dev-notification-topic with referenceId: <ref number>
    ```

___

#### To peek at the message queue in order to view the Trade JSON, follow these steps from within the Service Bus Explorer Application:

1. Go to `File > Connect`
2. From the Service Bus Namespaces dropdown, select `Enter connection string...`
3. Copy and Paste the connection string from the DEFRA Shared Secrets SharePoint page. Click `Save`
   and then `OK`
4. After a few seconds, you should see a list of Queues and Topics appear in the Left Pane.
   Select `dev-notification-topic` from `Topics`
5. To peek at the Messages in the queue, select `trade-platform` from the list of Subscriptions. On
   the right pane above the log pane, click on the `Messages` button
6. `Receive Mode: Peek`, `Message Count: 100 (just needs to be larger than the active message count)`.
   Leave the other field, and click OK
7. Scroll to the bottom of the Message List, the last message should be the Message you sent to the
   queue.

___

### FAQ

#### I have Certificate Errors when trying to use the Internet within the VM

- If you have zscaler installed and enabled on your machine, you will need to install the
  certificates within the Virtual
  Machine. [This article](https://help.zscaler.com/zia/configuration-example-importing-zscaler-root-certificate-ie-11)
  explains how to import the .p7b certificate, which can be obtained from the Kainos Zscaler
  SharePoint.
- The process is to copy across your .p7b root chain file, and add it into Windows
    - This can be achieved by typing `inetcpl.cpl` in the Start Menu to open the Internet Properties
      dialog
    - Go to `Content > Certificates`
    - Click `Import`, pick your p7b file, and place it in
      the `Trusted Root Certifications Authority` store
    - Restart Windows

#### I don't see the message in the Queue after following these steps

- Check the `requestTypeMap` in the constructor
  of `service/src/main/java/uk/gov/defra/tracesx/notification/service/TradeDispatchDecisionService.java`
  from the Notification-MS
- This provides an exhaustive mapping from what status changes will trigger this Trade Message to
  send. Ensure you are patching the notification with this status mapping in mind

___

## Trade -> IPAFFS 
* The component scan is important when controlling whether this should use cloning or
  enotification mapping files.
    * If you wish to do enotification mapping your component scan should include:
        * `uk.gov.defra.tracesx.common` and `uk.gov.defra.tracesx.enotification`
    * If you wish to do cloning mapping then your component scan should include:
        * `uk.gov.defra.tracesx.common` and `uk.gov.defra.tracesx.cloning`
    * Attempting to include both will result in a clash in beans as the mapper relies on interfaces
      when autowiring in the correct mapping beans.

