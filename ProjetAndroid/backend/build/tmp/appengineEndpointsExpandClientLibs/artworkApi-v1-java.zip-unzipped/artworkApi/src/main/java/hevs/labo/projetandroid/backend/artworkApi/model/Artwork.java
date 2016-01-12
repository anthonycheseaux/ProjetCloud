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
 * on 2016-01-12 at 10:39:35 UTC 
 * Modify at your own risk.
 */

package hevs.labo.projetandroid.backend.artworkApi.model;

/**
 * Model definition for Artwork.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the artworkApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Artwork extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String creationYear;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean exposed;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("foreign_key_Artist_id")
  private java.lang.Integer foreignKeyArtistId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("foreign_key_Room_id")
  private java.lang.Integer foreignKeyRoomId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer id;

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
  private java.lang.String type;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCreationYear() {
    return creationYear;
  }

  /**
   * @param creationYear creationYear or {@code null} for none
   */
  public Artwork setCreationYear(java.lang.String creationYear) {
    this.creationYear = creationYear;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Artwork setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getExposed() {
    return exposed;
  }

  /**
   * @param exposed exposed or {@code null} for none
   */
  public Artwork setExposed(java.lang.Boolean exposed) {
    this.exposed = exposed;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getForeignKeyArtistId() {
    return foreignKeyArtistId;
  }

  /**
   * @param foreignKeyArtistId foreignKeyArtistId or {@code null} for none
   */
  public Artwork setForeignKeyArtistId(java.lang.Integer foreignKeyArtistId) {
    this.foreignKeyArtistId = foreignKeyArtistId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getForeignKeyRoomId() {
    return foreignKeyRoomId;
  }

  /**
   * @param foreignKeyRoomId foreignKeyRoomId or {@code null} for none
   */
  public Artwork setForeignKeyRoomId(java.lang.Integer foreignKeyRoomId) {
    this.foreignKeyRoomId = foreignKeyRoomId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Artwork setId(java.lang.Integer id) {
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
  public Artwork setImagePath(java.lang.String imagePath) {
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
  public Artwork setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getType() {
    return type;
  }

  /**
   * @param type type or {@code null} for none
   */
  public Artwork setType(java.lang.String type) {
    this.type = type;
    return this;
  }

  @Override
  public Artwork set(String fieldName, Object value) {
    return (Artwork) super.set(fieldName, value);
  }

  @Override
  public Artwork clone() {
    return (Artwork) super.clone();
  }

}
