/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jca.codegenerator.code;

import org.jboss.jca.codegenerator.Definition;
import org.jboss.jca.codegenerator.MethodForConnection;
import org.jboss.jca.codegenerator.MethodParam;

import java.io.IOException;
import java.io.Writer;

/**
 * A connection impl class CodeGen.
 * 
 * @author Jeff Zhang
 * @version $Revision: $
 */
public class ConnImplCodeGen extends AbstractCodeGen
{

   /**
    * Output class
    * @param def definition
    * @param out Writer
    * @throws IOException ioException
    */
   @Override
   public void writeClassBody(Definition def, Writer out) throws IOException
   {

      out.write("public class " + getClassName(def) + " implements " + def.getConnInterfaceClass());
      writeLeftCurlyBracket(out, 0);
      int indent = 1;
      writeIndent(out, indent);
      out.write("/** The logger */");
      writeEol(out);
      writeIndent(out, indent);
      out.write("private static Logger log = Logger.getLogger(\"" + getClassName(def) + "\");");
      writeEol(out);
      writeEol(out);
      
      writeDefaultConstructor(def, out, indent);
      
      writeMethod(def, out, indent);
      writeRightCurlyBracket(out, 0);
   }
   
   /**
    * Output class import
    * @param def definition
    * @param out Writer
    * @throws IOException ioException
    */
   @Override
   public void writeImport(Definition def, Writer out) throws IOException
   {
      out.write("package " + def.getRaPackage() + ";");
      writeEol(out);
      writeEol(out);
      out.write("import java.util.logging.Logger;");
      writeEol(out);
      writeEol(out);
   }
   
   /**
    * get this class name
    * @param def definition
    * @return String class name
    */
   @Override
   public String getClassName(Definition def)
   {
      return def.getConnImplClass();
   }
   
   /**
    * Output methods
    * @param def definition
    * @param out Writer
    * @param indent space number
    * @throws IOException ioException
    */
   private void writeMethod(Definition def, Writer out, int indent) throws IOException
   {
      if (def.isDefineMethodInConnection())
      {
         if (def.getMethods().size() > 0)
         {
            for (MethodForConnection method : def.getMethods())
            {
               writeIndent(out, indent);
               out.write("/**");
               writeEol(out);
               writeIndent(out, indent);
               out.write(" * call " + method.getMethodName());
               writeEol(out);
               writeIndent(out, indent);
               out.write(" */");
               writeEol(out);
               
               writeIndent(out, indent);
               out.write("public " + method.getReturnType() + " " +
                  method.getMethodName() + "(");
               int paramSize = method.getParams().size();
               for (int i = 0; i < paramSize; i++)
               {
                  MethodParam param = method.getParams().get(i);
                  out.write(param.getType());
                  out.write(" ");
                  out.write(param.getName());
                  if (i + 1 < paramSize)
                     out.write(", ");
               }
               out.write(")");
               int exceptionSize = method.getExceptionType().size();
               for (int i = 0; i < exceptionSize; i++)
               {
                  if (i == 0)
                     out.write(" throws ");
                  String ex = method.getExceptionType().get(i);
                  out.write(ex);
                  if (i + 1 < exceptionSize)
                     out.write(", ");
               }
               writeLeftCurlyBracket(out, indent);
               writeIndent(out, indent + 1);
               out.write("log.info(\"call " + method.getMethodName() + "\");");
               writeEol(out);
               if (!method.getReturnType().equals("void"))
               {
                  writeIndent(out, indent + 1);
                  out.write("return null;");
                  writeEol(out);
               }

               writeRightCurlyBracket(out, indent);
            }
         }
      }
      else
      {
         writeIndent(out, indent);
         out.write("/**");
         writeEol(out);
         writeIndent(out, indent);
         out.write(" * call me");
         writeEol(out);
         writeIndent(out, indent);
         out.write(" */");
         writeEol(out);
         
         writeIndent(out, indent);
         out.write("public void callMe()");
         writeLeftCurlyBracket(out, indent);
         writeIndent(out, indent + 1);
         out.write("log.info(\"call callMe\");");

         writeRightCurlyBracket(out, indent);
      }
   }
}
