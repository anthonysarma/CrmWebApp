<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <link rel="stylesheet" href="resources/css/style.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <title>CRM</title>
      <style>
         body {
         font-family: Arial, Helvetica, sans-serif;
         }
         h1 {
         font-size: 1.5em;
         color: #525252;
         }
      </style>
      <script>
         $(document).ready(function() {
                     $("#regiser").click(function() {
                         $("#modal").modal({
                             backdrop: true
                         });
                     });
                 
                 });
      </script>
   </head>
   <body>
      <nav class="navbar navbar-inverse">
         <div class="container-fluid">
            <ul class="nav navbar-nav">
               <li class="active"><a href="#">Home</a></li>
               <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1 <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                     <li><a href="#">Page 1-1</a></li>
                     <li><a href="#">Page 1-2</a></li>
                     <li><a href="#">Page 1-3</a></li>
                  </ul>
               </li>
               <li><a href="#">About</a></li>
               <li><a href="#" id="regiser">Register</a></li>
            </ul>
         </div>
      </nav>
      <div class="box">
         <div class="column"></div>
         <div class="column"></div>
         <div class="column">
            <h1>Login</h1>
            <form method="POST" action="/crmWebApp/user/login" role="form">
               <input type="text" class="username" placeholder="UserName" name="userName" id="userName" required />
               <input type="password" class="password" placeholder="Password" name="password" id="password" required />
               <div class="row">
                  <button type="submit" class="logbtn">Login</button>
                  <button type="" id="closebtn">Close</button>
               </div>
            </form>
         </div>
      </div>
      <div class="modal fade" id="modal" role="dialog">
         <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
               <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title" align="center">Customer Register Form</h4>
               </div>
               <div class="modal-body">
                  <form method="POST" action="/crmWebApp/register/save" role="form">
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <label for="CustomerName">Customer Name:</label>
                        </div>
                        <div class="col-xs-3">
                           <input type="text" name="firstName" placeHolder="First Name" class="form-control" id="firstName" required />
                        </div>
                        <div class="col-xs-3">
                           <input type="text" placeHolder="Middle Name" class="form-control" id="middleName">
                        </div>
                        <div class="col-xs-3">
                           <input type="text" placeHolder="Last Name" class="form-control" id="lastName">
                        </div>
                     </div>
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <label for="SelectGender">Gender:</label>
                           </label>
                        </div>
                        <div class="col-xs-3">
                           <input class="form-check-input" name="gender" type="radio" value="male" checked="checked" />male
                           <input class="form-check-input" name="gender" type="radio" value="female" />female
                        </div>
                     </div>
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <label for="Email">Email:</label>
                           </label>
                        </div>
                        <div class="col-xs-9">
                           <input type="email" name="emailId" placeHolder="Email" class="form-control" id="emailId">
                        </div>
                     </div>
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <label for="Contact">Contact:</label>
                        </div>
                        <div class="col-xs-3">
                           <input type="text" name="phone" placeHolder="Phone" class="form-control" id="phone">
                        </div>
                        <div class="col-xs-3">
                           <input type="text" placeHolder="Mobile" class="form-control" id="mobile">
                        </div>
                     </div>
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <label class="form-check-label">Comments</label>
                        </div>
                        <div class="col-xs-8">
                           <textarea name="comments" cols="50" rows="10" id="comments"></textarea>
                        </div>
                     </div>
                     <div class="form-group row">
                        <div class="col-xs-3">
                           <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                     </div>
                  </form>
               </div>
               
            </div>
         </div>
      </div>
      <div class="footer">
         <p>Footer</p>
      </div>
   </body>
</html>
