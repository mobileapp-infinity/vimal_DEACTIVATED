-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
      public <init>(...);
   }
   -dontwarn retrofit2.**
   -dontwarn org.codehaus.mojo.**
   -keep class retrofit2.** { *; }
   -keepattributes Signature
   -keep class okio.** { *; }
   -keepattributes Exceptions
   -keepattributes *Annotation*
    -dontwarn javax.annotation.**