/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-01-08 17:48:37 UTC)
 * on 2016-01-12 at 22:49:31 UTC 
 * Modify at your own risk.
 */

package hevs.labo.projetandroid.backend.roomApi.model;

/**
 * Model definition for Room.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roomApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Room extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("image_path")
  private java.lang.String imagePath;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean selected;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double size;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Room setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImagePath() {
    return imagePath;
  }

  /**
   * @param imagePath imagePath or {@code null} for none
   */
  public Room setImagePath(java.lang.String imagePath) {
    this.imagePath = imagePath;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Room setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getSelected() {
    return selected;
  }

  /**
   * @param selected selected or {@code null} for none
   */
  public Room setSelected(java.lang.Boolean selected) {
    this.selected = selected;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getSize() {
    return size;
  }

  /**
   * @param size size or {@code null} for none
   */
  public Room setSize(java.lang.Double size) {
    this.size = size;
    return this;
  }

  @Override
  public Room set(String fieldName, Object value) {
    return (Room) super.set(fieldName, value);
  }

  @Override
  public Room clone() {
    return (Room) super.clone();
  }

}
