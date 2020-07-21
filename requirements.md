# Implement reset Password Api
 [POST /user/resetPassword]. 
 1. To this you pass a user and it generates a password reset
  token for you. 
  2. Implement another controller
     [POST /user/newPassword]. 
  3.  To this you pass new password, reset password token,
      email. You validate if the token is valid,
      email is for the user that was assigned that token. 
      If yes, you update their password. 
      Make sure you Encrypt the password 
      while updating as well. 
You will need to implement a new model for PasswordResetToken as well. The same controller should work
#Actions
 
