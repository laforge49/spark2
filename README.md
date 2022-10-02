# spark2

A Clojure library intended to breath some life into gems with a uniform, 
composable API.

Previously, the uniform API simply mandated that env, a persistent map, be passed as the only argument. In spark2, parameters are relegated to a second argument and (a potentially updated) first argument is the return value.

And where previously the value of each gem was held in its own atom under env, those atoms have been dropped and the gems held directly under the first argument, which has been renamed from env to gems.

Conformant functions can now be composed using the the clojure threading function, ->, or collected in a vector for subsequent evaluation as part of a performant journal entry gem.

## Usage

The top-level folder, spark2, should be opened as an [obsidian](https://obsidian.md) vault for enhanced access to .md files.

## License

Copyright © 2022 William la Forge Jr.

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
