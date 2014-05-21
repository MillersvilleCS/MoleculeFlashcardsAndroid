attribute vec4 in_position;
attribute vec4 inputImpostorSpaceCoordinate;

varying mediump vec2 impostorSpaceCoordinate;
varying mediump vec3 normalizedViewCoordinate;

uniform mat4 u_modelViewProjMatrix;
uniform mediump mat4 orthographicMatrix;
uniform mediump float u_radius;

void main()
{
    vec4 transformedPosition;
    transformedPosition = u_modelViewProjMatrix * in_position;
    impostorSpaceCoordinate = inputImpostorSpaceCoordinate.xy;

    transformedPosition.xy = transformedPosition.xy + inputImpostorSpaceCoordinate.xy * vec2(u_radius);
    transformedPosition = transformedPosition * orthographicMatrix;

    normalizedViewCoordinate = (transformedPosition.xyz + 1.0) / 2.0;
    gl_Position = transformedPosition;
}