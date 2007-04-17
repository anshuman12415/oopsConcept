/** 
  * This program (working title: MAS Prover) is an automated tableaux prover
  * for epistemic logic (S5n).
  * Copyright (C) 2007  Elske van der Vaart and Gert van Valkenhoef

  * This program is free software; you can redistribute it and/or modify it
  * under the terms of the GNU General Public License version 2 as published
  * by the Free Software Foundation.

  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.

  * You should have received a copy of the GNU General Public License along
  * with this program; if not, write to the Free Software Foundation, Inc.,
  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  */

package nl.rug.ai.mas.prover.tableau;

import java.util.*;
import nl.rug.ai.mas.prover.formula.*;

public class LabelInstance implements Label {
	private Label d_parent;
	private World d_world;
	private Agent d_agent;

	public LabelInstance(Label parent, World world, Agent agent) {
		d_parent = parent;
		d_world = world;
		d_agent = agent;
	}

	public LabelSubstitution match(Label other) {
		Substitution<World> sw = d_world.match(other.d_world);
		Substitution<Agent> sa = d_agent.match(other.d_agent);
		LabelSubstitution s = new LabelSubstitution(sw, sa);

		LabelSubstitution ps = d_parent.match(other.d_parent);

		if (s.merge(ps)) {
			return s;
		}
		return null;
	}

	public Label substitute(LabelSubstitution s) {
		Agent a = d_agent.substitute(s.getAgentSubstitution());
		World w = d_world.substitute(s.getWorldSubstitution());
		Label p = d_parent.substitute(s);
		return new Label(p, w, a);
	}
}