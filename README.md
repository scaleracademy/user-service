# Implement reset Password features. 

#####1. api : [POST /user/resetPassword]. 
  -> Pass a user and it generates a password reset
  token for you. 
##### 2. api :  [POST /user/newPassword]. 
  -> Implement another controller
      You pass new password, reset password token,
      email. You validate if the token is valid,
      email is for the user that was assigned that token. 
      If yes, you update their password. 
      Make sure you Encrypt the password 
      while updating as well. 
You will need to implement a new model for PasswordResetToken as well. The same controller should work
#Actions
1. Create Event and Event Listener to reset the password. 
 
