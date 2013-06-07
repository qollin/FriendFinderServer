== INSTALL ==

To start the Android Training server, simply run

  mvn exec:java

To test the server navigate to 

  http://localhost:9998/helloworld
  
For accessing the FriedFinder functions, call the following URLs

  http://localhost:9998/login
  http://localhost:9998/setlocation
  http://localhost:9998/setprofilepicture

You have to use the classes from the

  de.inovex.academy.android.server.dto
  
package to call these methods. Have a look at the

  getMessage
  
method in the classes in the 

  de.inovex.academy.android.server.resource

package for the signature of the method you're calling.

  
