/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.test.commandline;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import schemacrawler.tools.commandline.Command;
import schemacrawler.tools.commandline.CommandParser;

public class CommandParserTest
{

  @Test
  public void noArgs()
  {
    final String[] args = new String[0];

    final CommandParser optionsParser = new CommandParser();

    assertThrows(CommandLine.MissingParameterException.class,
                 () -> optionsParser.parse(args));
  }

  @Test
  public void noValidArgs()
  {
    final String[] args = { "--some-option" };

    final CommandParser optionsParser = new CommandParser();

    assertThrows(CommandLine.MissingParameterException.class,
                 () -> optionsParser.parse(args));
  }

  @Test
  public void commandNoValue()
  {
    final String[] args = { "--command" };

    final CommandParser optionsParser = new CommandParser();
    assertThrows(CommandLine.MissingParameterException.class,
                 () -> optionsParser.parse(args));
  }

  @Test
  public void blankCommand()
  {
    final String[] args = {
      "--command", " " };

    final CommandParser optionsParser = new CommandParser();
    assertThrows(CommandLine.ParameterException.class,
                 () -> optionsParser.parse(args));
  }

  @Test
  public void allArgs()
  {
    final String[] args = {
      "--command", "a_command", "additional", "--extra" };

    final CommandParser optionsParser = new CommandParser();
    optionsParser.parse(args);
    final Command options = optionsParser.getCommand();

    assertThat(options.toString(), is("a_command"));

    final String[] remainder = optionsParser.getRemainder();
    assertThat(remainder, is(new String[] {
      "additional", "--extra" }));
  }

}
