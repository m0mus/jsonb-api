/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package jakarta.json.bind.tck.customizedmapping.serializers.model.serializer;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.bind.tck.customizedmapping.serializers.model.Animal;
import jakarta.json.stream.JsonGenerator;

import java.util.List;

public class AnimalListSerializer implements JsonbSerializer<List<Animal>> {
  private AnimalSerializer animalSerializer = new AnimalSerializer();

  public void serialize(List<Animal> animals, JsonGenerator jsonGenerator,
      SerializationContext serializationContext) {
    jsonGenerator.writeStartArray();
    for (Animal animal : animals) {
      animalSerializer.serialize(animal, jsonGenerator, serializationContext);
    }
    jsonGenerator.writeEnd();
  }
}
