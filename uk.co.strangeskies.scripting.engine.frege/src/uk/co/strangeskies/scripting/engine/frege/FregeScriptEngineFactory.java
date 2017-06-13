/*
 * Copyright (C) 2017 Elias N Vasylenko <eliasvasylenko@strangeskies.co.uk>
 *      __   _______  ____           _       __     _      __       __
 *    ,`_ `,|__   __||  _ `.        / \     |  \   | |  ,-`__`¬  ,-`__`¬
 *   ( (_`-'   | |   | | ) |       / . \    | . \  | | / .`  `' / .`  `'
 *    `._ `.   | |   | |<. L      / / \ \   | |\ \ | || |    _ | '--.
 *   _   `. \  | |   | |  `.`.   / /   \ \  | | \ \| || |   | || +--'
 *  \ \__.' /  | |   | |    \ \ / /     \ \ | |  \ ` | \ `._' | \ `.__,.
 *   `.__.-`   |_|   |_|    |_|/_/       \_\|_|   \__|  `-.__.J  `-.__.J
 *                   __    _         _      __      __
 *                 ,`_ `, | |  _    | |  ,-`__`¬  ,`_ `,
 *                ( (_`-' | | ) |   | | / .`  `' ( (_`-'
 *                 `._ `. | L-' L   | || '--.     `._ `.
 *                _   `. \| ,.-^.`. | || +--'    _   `. \
 *               \ \__.' /| |    \ \| | \ `.__,.\ \__.' /
 *                `.__.-` |_|    |_||_|  `-.__.J `.__.-`
 *
 * This file is part of uk.co.strangeskies.scripting.engine.frege.
 *
 * uk.co.strangeskies.scripting.engine.frege is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * uk.co.strangeskies.scripting.engine.frege is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.strangeskies.scripting.engine.frege;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import frege.scriptengine.FregeScriptEngine.JFregeScriptEngine;
import uk.co.strangeskies.scripting.InvocableBase;

@SuppressWarnings("javadoc")
public class FregeScriptEngineFactory implements ScriptEngineFactory {
	private static final class FregeScriptEngineFactoryImpl
			extends frege.scriptengine.FregeScriptEngine.FregeScriptEngineFactory {
		@Override
		public ScriptEngine getScriptEngine() {
			return new InvocableFregeScriptEngine(this);
		}
	}

	private static final class InvocableFregeScriptEngine extends JFregeScriptEngine implements InvocableBase {
		public InvocableFregeScriptEngine(ScriptEngineFactory factory) {
			super(factory);
		}

		@Override
		public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
			StringBuilder expressionBuilder = new StringBuilder(name);
			int i = 0;
			for (Object argument : args) {
				String argumentName = "b" + i++;
				put(argumentName, argument);
				expressionBuilder.append(' ').append(argumentName);
			}

			return eval(expressionBuilder.toString());
		}
	}

	private final ScriptEngineFactory component = new FregeScriptEngineFactoryImpl();

	@Override
	public ScriptEngine getScriptEngine() {
		return component.getScriptEngine();
	}

	@Override
	public String getEngineName() {
		return component.getEngineName();
	}

	@Override
	public String getEngineVersion() {
		return component.getEngineVersion();
	}

	@Override
	public List<String> getExtensions() {
		return component.getExtensions();
	}

	@Override
	public List<String> getMimeTypes() {
		return component.getMimeTypes();
	}

	@Override
	public List<String> getNames() {
		return component.getNames();
	}

	@Override
	public String getLanguageName() {
		return component.getLanguageName();
	}

	@Override
	public String getLanguageVersion() {
		return component.getLanguageVersion();
	}

	@Override
	public Object getParameter(String key) {
		return component.getParameter(key);
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		return component.getMethodCallSyntax(obj, m, args);
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		return component.getOutputStatement(toDisplay);
	}

	@Override
	public String getProgram(String... statements) {
		return component.getProgram(statements);
	}
}
