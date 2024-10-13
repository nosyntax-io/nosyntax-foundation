#!/usr/bin/env python3

import argparse
import json
import logging
import os
from jinja2 import Environment, FileSystemLoader, select_autoescape
from typing import Union, Dict, Any

def render_template(
	template_path: str,
	output_path: str,
	data: Dict[str, Any]
) -> None:
	"""
	Renders a template using data and saves the output to a file.

	:param template_path: Path to the template file.
	:param output_path: Path to save the rendered output.
	:param data: Dictionary of data for template rendering.
	"""
	try:
		env = Environment(
			loader=FileSystemLoader(os.path.dirname(template_path)),
			autoescape=select_autoescape()
		)

		template = env.get_template(os.path.basename(template_path))

		with open(output_path, 'w', encoding='utf-8') as f:
			f.write(template.render(**data))

		logging.info(f"Template rendered and saved to '{output_path}'.")
	except Exception as e:
		raise RuntimeError(f"Error rendering template: {e}") from e
	
def parse_data(data: Union[str, Dict[str, Any]]) -> Dict[str, Any]:
	"""
	Parses data from a JSON file, raw JSON string, or a dictionary.

	:param data: Path to a JSON file, a raw JSON string, or a dictionary.
	:return: Parsed data as a dictionary.
	"""
	if isinstance(data, dict):
		return data

	try:
		if isinstance(data, str) and os.path.isfile(data):
			with open(data, 'r', encoding='utf-8') as f:
				return json.load(f)
		return json.loads(data)
	except Exception as e:
		raise ValueError(f"Error decoding JSON: {e}") from e

def main() -> None:
	logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

	parser = argparse.ArgumentParser(description="Renders a template using data and saves the output to a file.")
	parser.add_argument("--template-file", "-t", required=True, help="Path to the template file.")
	parser.add_argument("--output-file", "-o", required=True, help="Path to save the rendered output.")
	parser.add_argument("--data", "-d", required=True, help="Path to a JSON file, a raw JSON string, or a dictionary.")

	args = parser.parse_args()

	try:
		parsed_data = parse_data(args.data)
		render_template(args.template_file, args.output_file, parsed_data)
	except Exception as e:
		logging.error(e)

if __name__ == "__main__":
	main()