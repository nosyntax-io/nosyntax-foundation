#!/usr/bin/env python3

import argparse
import json
import logging
import os

from jinja2 import Environment, FileSystemLoader, select_autoescape

def render_template(template_path, output_path, data_content):
  """
  Render a template file using data from a JSON file or JSON string.

  :param template_path: Path to the template file
  :param output_path: Path to the rendered output file
  :param data_content: JSON data content as a dictionary
  """
  try:
    env = Environment(
      loader=FileSystemLoader(os.path.dirname(template_path)),
      autoescape=select_autoescape()
    )

    template = env.get_template(os.path.basename(template_path))

    rendered_content = template.render(**data_content)

    with open(output_path, 'w') as f:
      f.write(rendered_content)

    logging.info(f"Template rendered successfully. Output saved to {output_path}")

  except Exception as e:
    logging.error(f"Error: {e}")
    exit(1)

def parse_data(data):
  """
  Parse data as JSON. Determine if it's a file path or a JSON string.

  :param data: JSON data as a file path or string
  :return: Parsed JSON data as a dictionary
  """
  if os.path.isfile(data):
    with open(data, 'r') as f:
      return json.load(f)
  else:
    try:
      return json.loads(data)
    except json.JSONDecodeError as e:
      logging.error(f"Invalid JSON data provided: {e}")
      exit(1)

def main():
  logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

  parser = argparse.ArgumentParser(description="Render a template using data from a JSON file or JSON string")
  parser.add_argument("--template-file", "-t", required=True, help="Path to the template file")
  parser.add_argument("--output-file", "-o", required=True, help="Path to the rendered output file")
  parser.add_argument("--data", "-d", required=True, help="Path to the JSON data file or JSON string")
  args = parser.parse_args()

  data_content = parse_data(args.data)

  render_template(args.template_file, args.output_file, data_content)

if __name__ == "__main__":
  main()