#!/usr/bin/env python3

import sys
import os
import argparse
import logging
from jinja2 import Environment, FileSystemLoader, select_autoescape

def render_properties_template(template_path, output_path, context):
  """
  Renders a properties template file using the provided context and writes the result to the output file.

  :param template_path: Path to the template file
  :param output_path: Path to the output file where the rendered content will be saved
  :param context: Dictionary containing key-value pairs for template rendering
  """
  try:
    env = Environment(
      loader=FileSystemLoader(os.path.dirname(template_path)),
      autoescape=select_autoescape()
    )

    template = env.get_template(os.path.basename(template_path))

    rendered_content = template.render(context)

    with open(output_path, 'w') as output_file:
      output_file.write(rendered_content)

    logging.info(f"Template successfully rendered to: {output_path}")

  except FileNotFoundError:
    logging.error(f"Template file not found: {template_path}")
    sys.exit(1)
  except Exception as e:
    logging.error(f"Error rendering template: {e}")
    sys.exit(1)

def parse_context(context_args):
  """
  Parses a list of context arguments into a dictionary.

  :param context_args: List of key-value pairs in the form key=value
  :return: Dictionary with key-value pairs for template rendering
  """
  context = {}
  for pair in context_args:
    try:
      key, value = pair.split('=', 1)
      context[key.strip()] = value.strip()
    except ValueError:
      logging.warning(f"Ignoring malformed key-value pair: {pair}")
  return context

if __name__ == "__main__":
  logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

  parser = argparse.ArgumentParser(description="Render properties template file")
  parser.add_argument("--template-file", required=True, help="Path to the template file")
  parser.add_argument("--output-file", required=True, help="Path to save the rendered output")
  parser.add_argument("--context", nargs='*', help="Context key-value pairs for template rendering (e.g., key1=value1 key2=value2)")
  args = parser.parse_args()

  context = parse_context(args.context)

  render_properties_template(args.template_file, args.output_file, context)